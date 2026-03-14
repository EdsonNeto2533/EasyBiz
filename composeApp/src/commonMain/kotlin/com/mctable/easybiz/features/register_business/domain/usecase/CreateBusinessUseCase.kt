package com.mctable.easybiz.features.register_business.domain.usecase

import com.mctable.easybiz.features.register_business.domain.entity.BusinessProfileEntity
import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

class CreateBusinessUseCase(private val repository: RegisterBusinessRepository) {
    suspend fun execute(
        name: String,
        category: String,
        latitude: Double,
        longitude: Double,
        completeAddress: String
    ): Result<BusinessProfileEntity> {
        return repository.createBusiness(name, category, latitude, longitude, completeAddress)
    }
}
