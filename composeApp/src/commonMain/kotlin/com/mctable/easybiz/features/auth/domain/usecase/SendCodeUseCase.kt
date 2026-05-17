package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface SendCodeUseCase {
    suspend fun execute(email: String): Result<Unit>
}

class SendCodeUseCaseImpl(
    private val repository: LoginRepository
) : SendCodeUseCase {
    override suspend fun execute(email: String): Result<Unit> {
        return repository.sendCode(email)
    }
}
