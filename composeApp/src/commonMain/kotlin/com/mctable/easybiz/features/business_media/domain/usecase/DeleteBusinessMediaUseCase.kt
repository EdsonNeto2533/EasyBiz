package com.mctable.easybiz.features.business_media.domain.usecase

import com.mctable.easybiz.features.business_media.domain.repository.BusinessMediaRepository

interface DeleteBusinessMediaUseCase {
    suspend fun execute(businessId: String, mediaId: String): Result<Unit>
}

class DeleteBusinessMediaUseCaseImpl(
    private val repository: BusinessMediaRepository
) : DeleteBusinessMediaUseCase {
    override suspend fun execute(businessId: String, mediaId: String) =
        repository.deleteMedia(businessId, mediaId)
}