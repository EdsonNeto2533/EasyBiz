package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.data.model.VerifyEmailResponseModel
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface VerifyEmailUseCase {
    suspend fun execute(email: String, code: String): Result<VerifyEmailResponseModel>
}

class VerifyEmailUseCaseImpl(
    private val repository: LoginRepository
) : VerifyEmailUseCase {
    override suspend fun execute(email: String, code: String): Result<VerifyEmailResponseModel> {
        return repository.verifyEmail(email, code)
    }
}
