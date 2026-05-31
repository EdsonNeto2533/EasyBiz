package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface ForgetPasswordUseCase {
    suspend fun execute(email: String): Result<Unit>
}

class ForgetPasswordUseCaseImpl(
    private val repository: LoginRepository
) : ForgetPasswordUseCase {
    override suspend fun execute(email: String): Result<Unit> {
        return repository.forgetPassword(email)
    }
}
