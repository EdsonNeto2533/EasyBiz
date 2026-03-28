package com.mctable.easybiz.features.register_business.domain.usecase

import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

interface AddLogoUseCase {
    suspend fun execute(id: String, imageBytes: ByteArray): Result<Unit>
}

class AddLogoUseCaseImpl(private val repository: RegisterBusinessRepository) : AddLogoUseCase {
    override suspend fun execute(id: String, imageBytes: ByteArray): Result<Unit> {
        return repository.addLogo(id, imageBytes)
    }
}
