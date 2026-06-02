package com.mctable.easybiz.features.business_media.domain.usecase

import com.mctable.easybiz.features.business_media.domain.repository.BusinessMediaRepository

interface AddBusinessMediaUseCase {
    suspend fun execute(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String): Result<Unit>
}

class AddBusinessMediaUseCaseImpl(
    private val repository: BusinessMediaRepository
) : AddBusinessMediaUseCase {
    override suspend fun execute(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String) =
        repository.addMedia(businessId, fileBytes, mimeType, fileName)
}