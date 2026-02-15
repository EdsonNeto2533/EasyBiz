package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface RegisterUseCase {
    suspend fun execute(email: String, password: String, name: String): Result<LoginEntity>
}

class RegisterUseCaseImpl(
    private val loginRepository: LoginRepository
) : RegisterUseCase {
    override suspend fun execute(
        email: String,
        password: String,
        name: String
    ): Result<LoginEntity> {
        return loginRepository.register(email, password, name)
    }
}
