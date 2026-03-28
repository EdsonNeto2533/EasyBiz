package com.mctable.easybiz.features.register_business.domain.usecase

import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

class AddLogoUseCase(private val repository: RegisterBusinessRepository) {
    suspend fun execute(id: String, imageBytes: ByteArray): Result<Unit> {
        return repository.addLogo(id, imageBytes)
    }
}
