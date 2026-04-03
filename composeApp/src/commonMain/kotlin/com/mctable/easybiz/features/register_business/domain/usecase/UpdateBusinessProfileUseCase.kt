package com.mctable.easybiz.features.register_business.domain.usecase

import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

interface UpdateBusinessProfileUseCase {
    suspend fun execute(
        id: String,
        description: String,
        telephone: String,
        minimumPrice: Double,
        yearsOfExperience: Int,
        workingHours: String
    ): Result<Unit>

}

class UpdateBusinessProfileUseCaseImpl(
    private val repository: RegisterBusinessRepository
) : UpdateBusinessProfileUseCase {
    override suspend fun execute(
        id: String,
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
