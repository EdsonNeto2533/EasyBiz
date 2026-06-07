package com.mctable.easybiz.core.notification.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.core.notification.data.request.RegisterFcmTokenRequest

interface NotificationRemoteDataSource {
    suspend fun registerFcmToken(token: String): Result<Unit>
}

class NotificationRemoteDataSourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : NotificationRemoteDataSource {

    override suspend fun registerFcmToken(token: String): Result<Unit> {
        return networking.put(
            host = appEnv.host,
            path = "/usuarios/me/fcm-token",
            body = RegisterFcmTokenRequest(token),
            responseMapper = { }
        )
    }
}