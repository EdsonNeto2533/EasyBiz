package com.mctable.easybiz.features.register_business.domain.usecase

import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

class UpdateBusinessProfileUseCase(private val repository: RegisterBusinessRepository) {
    suspend fun execute(
        id: Int,
        description: String,
        telephone: String,
        minimumPrice: Double,
        yearsOfExperience: Int,
        workingHours: String
    ): Result<Unit> {
        return repository.updateProfile(
            id,
            description,
            telephone,
            minimumPrice,
            yearsOfExperience,
            workingHours
        )
    }
}
