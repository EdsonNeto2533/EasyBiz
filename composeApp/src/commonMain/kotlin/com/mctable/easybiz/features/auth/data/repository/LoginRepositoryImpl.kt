package com.mctable.easybiz.features.auth.data.repository

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.core.navigation.UserData
import com.mctable.easybiz.core.navigation.userChannel
import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSource
import com.mctable.easybiz.features.auth.data.mapper.LoginMapper
import com.mctable.easybiz.features.auth.data.model.LoginResponseModel
import com.mctable.easybiz.features.auth.data.model.VerifyEmailResponseModel
import com.mctable.easybiz.features.auth.data.request.ResetPasswordRequest
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource,
    private val easyBizStorage: EasyBizStorage
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<LoginEntity> = runCatching {
        return remoteDataSource.login(email, password).mapCatching { responseModel ->
            saveSession(responseModel, email)
            LoginMapper.toDomain(responseModel)
        }
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        registerToken: String
    ): Result<LoginEntity> = runCatching {
        return remoteDataSource.register(email, password, name, registerToken)
            .map { responseModel ->
                saveSession(responseModel, email)
                LoginMapper.toDomain(responseModel)
            }
    }

    override suspend fun verifyEmail(
        email: String,
        code: String
    ): Result<VerifyEmailResponseModel> = runCatching {
        return remoteDataSource.verifyEmail(email, code)
    }

    override suspend fun sendCode(email: String): Result<Unit> = runCatching {
        return remoteDataSource.sendCode(email)
    }

    override suspend fun logout(refreshToken: String): Result<Unit> = runCatching {
        return remoteDataSource.logout(refreshToken).onSuccess {
            clearSession()
        }
    }

    override suspend fun deleteAccount(): Result<Unit> = runCatching {
        return remoteDataSource.deleteAccount().onSuccess {
            clearSession()
        }
    }

    override suspend fun forgetPassword(email: String): Result<Unit> = runCatching {
        return remoteDataSource.forgetPassword(email)
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit> = runCatching {
        return remoteDataSource.resetPassword(request)
    }

    private suspend fun saveSession(responseModel: LoginResponseModel, email: String) {
        easyBizStorage.setString("token", responseModel.token)
        responseModel.refreshToken?.let {
            easyBizStorage.setString("refreshToken", it)
        }
        responseModel.userId?.let {
            easyBizStorage.setString("userId", it)
        }
        updateUserData(responseModel, email)
    }

    private suspend fun clearSession() {
        easyBizStorage.removeValue("token")
        easyBizStorage.removeValue("refreshToken")
        easyBizStorage.removeValue("userId")
    }

    private suspend fun updateUserData(userData: LoginResponseModel, email: String) = runCatching {
        userChannel.send(UserData(userData.name ?: "", email, userData.photoUrl))
    }
}
