package com.mctable.easybiz.features.auth.data.repository

import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSource
import com.mctable.easybiz.features.auth.data.mapper.LoginMapper
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

class LoginRepositoryImpl(
    private val remoteDataSource: LoginRemoteDataSource
) : LoginRepository {

    override suspend fun login(email: String, password: String): Result<LoginEntity> {
        return remoteDataSource.login(email, password).mapCatching { responseModel ->
            LoginMapper.toDomain(responseModel)
        }
    }
}
