package com.mctable.easybiz.core.notification.domain.usecase

import com.mctable.easybiz.core.notification.data.datasource.NotificationRemoteDataSource

interface RegisterFcmTokenUseCase {
    suspend fun execute(token: String): Result<Unit>
}

class RegisterFcmTokenUseCaseImpl(
    private val dataSource: NotificationRemoteDataSource
) : RegisterFcmTokenUseCase {
    override suspend fun execute(token: String): Result<Unit> {
        return dataSource.registerFcmToken(token)
    }
}