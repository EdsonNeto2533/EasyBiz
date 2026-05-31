package com.mctable.easybiz.features.auth.domain.usecase

import com.mctable.easybiz.features.auth.domain.repository.LoginRepository

interface DeleteAccountUseCase {
    suspend fun execute(): Result<Unit>
}

class DeleteAccountUseCaseImpl(
    private val repository: LoginRepository
) : DeleteAccountUseCase {
    override suspend fun execute(): Result<Unit> {
        return repository.deleteAccount()
    }
}
