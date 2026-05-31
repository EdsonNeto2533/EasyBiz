package com.mctable.easybiz.features.auth.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.auth.data.mapper.LoginMapper
import com.mctable.easybiz.features.auth.data.model.LoginResponseModel
import com.mctable.easybiz.features.auth.data.model.VerifyEmailResponseModel
import com.mctable.easybiz.features.auth.data.request.ForgetPasswordRequest
import com.mctable.easybiz.features.auth.data.request.LoginRequestModel
import com.mctable.easybiz.features.auth.data.request.LogoutRequest
import com.mctable.easybiz.features.auth.data.request.RegisterRequest
import com.mctable.easybiz.features.auth.data.request.ResetPasswordRequest
import com.mctable.easybiz.features.auth.data.request.SendCodeRequest
import com.mctable.easybiz.features.auth.data.request.VerifyEmailRequest

interface LoginRemoteDataSource {
    suspend fun login(email: String, password: String): Result<LoginResponseModel>
    suspend fun register(
        email: String,
        password: String,
        name: String,
        registerToken: String
    ): Result<LoginResponseModel>

    suspend fun verifyEmail(email: String, code: String): Result<VerifyEmailResponseModel>
    suspend fun sendCode(email: String): Result<Unit>
    suspend fun logout(refreshToken: String): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun forgetPassword(email: String): Result<Unit>
    suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit>
}

class LoginRemoteDataSourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : LoginRemoteDataSource {

    override suspend fun login(
        email: String,
        password: String
    ): Result<LoginResponseModel> {
        val request = LoginRequestModel(email, password)

        return networking.post(
            host = appEnv.host,
            path = "/auth/login",
            body = request,
            responseMapper = { jsonString ->
                LoginMapper.parseResponse(jsonString)
            }
        )
    }

    override suspend fun register(
        email: String,
        password: String,
        name: String,
        registerToken: String
    ): Result<LoginResponseModel> {
        val request = RegisterRequest(email, password, name, registerToken)

        return networking.post(
            host = appEnv.host,
            path = "/usuarios",
            body = request,
            responseMapper = { jsonString ->
                LoginMapper.parseResponse(jsonString)
            }
        )
    }

    override suspend fun verifyEmail(email: String, code: String): Result<VerifyEmailResponseModel> {
        val request = VerifyEmailRequest(email, code)
        return networking.post(
            host = appEnv.host,
            path = "/auth/verificar-codigo-cadastro",
            body = request,
            responseMapper = { jsonString ->
                LoginMapper.parseVerifyResponse(jsonString)
            }
        )
    }

    override suspend fun sendCode(email: String): Result<Unit> {
        val request = SendCodeRequest(email)
        return networking.post(
            host = appEnv.host,
            path = "/auth/enviar-codigo-cadastro",
            body = request,
            responseMapper = { }
        )
    }

    override suspend fun logout(refreshToken: String): Result<Unit> {
        val request = LogoutRequest(refreshToken)
        return networking.post(
            host = appEnv.host,
            path = "/auth/logout",
            body = request,
            responseMapper = { }
        )
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return networking.delete(
            host = appEnv.host,
            path = "/usuarios/me",
            responseMapper = { }
        )
    }

    override suspend fun forgetPassword(email: String): Result<Unit> {
        val request = ForgetPasswordRequest(email)
        return networking.post(
            host = appEnv.host,
            path = "/auth/esqueci-senha",
            body = request,
            responseMapper = { }
        )
    }

    override suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit> {
        return networking.post(
            host = appEnv.host,
            path = "/auth/redefinir-senha",
            body = request,
            responseMapper = { }
        )
    }
}
