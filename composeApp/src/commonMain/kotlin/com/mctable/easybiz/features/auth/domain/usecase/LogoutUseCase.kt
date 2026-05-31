package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface LogoutUseCase {
    suspend fun execute(): Result<Unit>
}

class LogoutUseCaseImpl(
    private val repository: LoginRepository,
    private val storage: EasyBizStorage
) : LogoutUseCase {
    override suspend fun execute(): Result<Unit> {
        val refreshToken = storage.getString("refreshToken") ?: ""
        return repository.logout(refreshToken)
    }
}
