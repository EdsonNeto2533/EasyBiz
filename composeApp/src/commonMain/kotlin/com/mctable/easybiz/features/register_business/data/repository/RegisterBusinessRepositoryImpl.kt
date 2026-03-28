package com.mctable.easybiz.features.register_business.data.repository

import com.mctable.easybiz.features.register_business.data.datasource.RegisterBusinessDatasource
import com.mctable.easybiz.features.register_business.data.dto.CreateBusinessRequest
import com.mctable.easybiz.features.register_business.data.dto.UpdateProfileRequest
import com.mctable.easybiz.features.register_business.data.mapper.RegisterBusinessMapper
import com.mctable.easybiz.features.register_business.domain.entity.BusinessProfileEntity
import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository

class RegisterBusinessRepositoryImpl(
    private val datasource: RegisterBusinessDatasource
) : RegisterBusinessRepository {

    override suspend fun createBusiness(
        name: String,
        category: String,
        latitude: Double,
        longitude: Double,
        completeAddress: String
    ): Result<BusinessProfileEntity> = runCatching {
        val request = CreateBusinessRequest(name, category, latitude, longitude, completeAddress)
        return datasource.createBusiness(request).map {
            RegisterBusinessMapper.toEntity(it)
        }
    }

    override suspend fun updateProfile(
        id: String,
        description: String,
        telephone: String,
        minimumPrice: Double,
        yearsOfExperience: Int,
        workingHours: String
    ): Result<Unit> = runCatching {
        val request = UpdateProfileRequest(
            description,
            telephone,
            minimumPrice,
            yearsOfExperience,
            workingHours
        )
        return datasource.updateProfile(id, request)
    }

    override suspend fun addLogo(id: String, imageBytes: ByteArray): Result<Unit> = runCatching {
        return datasource.addLogo(id, imageBytes)
    }
}
