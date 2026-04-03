package com.mctable.easybiz.features.register_business.domain.repository

import com.mctable.easybiz.features.register_business.domain.entity.BusinessProfileEntity

interface RegisterBusinessRepository {
    suspend fun createBusiness(
        name: String,
        category: String,
        latitude: Double,
        longitude: Double,
        completeAddress: String
    ): Result<BusinessProfileEntity>

    suspend fun updateProfile(
        id: String,
        description: String,
        telephone: String,
        minimumPrice: Double,
        yearsOfExperience: Int,
        workingHours: String
    ): Result<Unit>

    suspend fun addLogo(id: String, imageBytes: ByteArray): Result<Unit>
}
