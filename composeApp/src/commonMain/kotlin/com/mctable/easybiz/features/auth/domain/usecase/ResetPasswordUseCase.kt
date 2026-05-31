package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.data.request.ResetPasswordRequest
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface ResetPasswordUseCase {
    suspend fun execute(request: ResetPasswordRequest): Result<Unit>
}

class ResetPasswordUseCaseImpl(
    private val repository: LoginRepository
) : ResetPasswordUseCase {
    override suspend fun execute(request: ResetPasswordRequest): Result<Unit> {
        return repository.resetPassword(request)
    }
}
