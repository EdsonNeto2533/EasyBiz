package com.mctable.easybiz.features.auth.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.auth.data.mapper.LoginMapper
import com.mctable.easybiz.features.auth.data.model.LoginResponseModel
import com.mctable.easybiz.features.auth.data.request.LoginRequestModel
import com.mctable.easybiz.features.auth.data.request.RegisterRequest

interface LoginRemoteDataSource {
    suspend fun login(email: String, password: String): Result<LoginResponseModel>
    suspend fun register(email: String, password: String, name: String): Result<LoginResponseModel>
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
        name: String
    ): Result<LoginResponseModel> {
        val request = RegisterRequest(email, password, name)

        return networking.post(
            host = appEnv.host,
            path = "/usuarios",
            body = request,
            responseMapper = { jsonString ->
                LoginMapper.parseResponse(jsonString)
            }
        )
    }
}
