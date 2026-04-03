package com.mctable.easybiz.features.business_details.domain.usecase

import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository

interface GetBusinessDetailsUseCase {
    suspend fun execute(id: String): Result<BusinessDetailsEntity>
}

class GetBusinessDetailsUseCaseImpl(
    private val repository: BusinessDetailsRepository
): GetBusinessDetailsUseCase {
    override suspend fun execute(id: String): Result<BusinessDetailsEntity> {
        return repository.getBusinessDetails(id)
    }
}
