package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface VerifyEmailUseCase {
    suspend fun execute(email: String, code: String): Result<Unit>
}

class VerifyEmailUseCaseImpl(
    private val repository: LoginRepository
) : VerifyEmailUseCase {
    override suspend fun execute(email: String, code: String): Result<Unit> {
        return repository.verifyEmail(email, code)
    }
}
