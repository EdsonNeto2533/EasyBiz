package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface LoginUseCase {
    suspend fun execute(email: String, password: String): Result<LoginEntity>
}

class LoginUseCaseImpl(
    private val loginRepository: LoginRepository
) : LoginUseCase {
    override suspend fun execute(email: String, password: String): Result<LoginEntity> {
        return loginRepository.login(email, password)
    }
}
