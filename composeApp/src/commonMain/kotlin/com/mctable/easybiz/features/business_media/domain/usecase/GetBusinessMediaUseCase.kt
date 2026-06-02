package com.mctable.easybiz.features.business_media.domain.usecase

import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity
import com.mctable.easybiz.features.business_media.domain.repository.BusinessMediaRepository

interface GetBusinessMediaUseCase {
    suspend fun execute(businessId: String): Result<List<BusinessMediaEntity>>
}

class GetBusinessMediaUseCaseImpl(
    private val repository: BusinessMediaRepository
) : GetBusinessMediaUseCase {
    override suspend fun execute(businessId: String) = repository.getMedia(businessId)
}