package com.mctable.easybiz.features.business_media.domain.repository

import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity

interface BusinessMediaRepository {
    suspend fun getMedia(businessId: String): Result<List<BusinessMediaEntity>>
    suspend fun addMedia(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String): Result<Unit>
    suspend fun deleteMedia(businessId: String, mediaId: String): Result<Unit>
}