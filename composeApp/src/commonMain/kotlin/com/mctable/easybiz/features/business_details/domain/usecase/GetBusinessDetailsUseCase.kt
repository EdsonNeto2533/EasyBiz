package com.mctable.easybiz.features.business_details.domain.usecase

import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository

class GetBusinessDetailsUseCase(
    private val repository: BusinessDetailsRepository
) {
    suspend fun execute(id: Int): Result<BusinessDetailsEntity> {
        return repository.getBusinessDetails(id)
    }
}
