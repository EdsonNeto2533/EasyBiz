package com.mctable.easybiz.features.business_media.data.repository

import com.mctable.easybiz.features.business_media.data.datasource.BusinessMediaDatasource
import com.mctable.easybiz.features.business_media.data.mapper.BusinessMediaMapper
import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity
import com.mctable.easybiz.features.business_media.domain.repository.BusinessMediaRepository

class BusinessMediaRepositoryImpl(
    private val datasource: BusinessMediaDatasource
) : BusinessMediaRepository {

    override suspend fun getMedia(businessId: String): Result<List<BusinessMediaEntity>> =
        datasource.getMedia(businessId).map { list -> list.map { BusinessMediaMapper.toEntity(it) } }

    override suspend fun addMedia(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String): Result<Unit> =
        datasource.addMedia(businessId, fileBytes, mimeType, fileName)

    override suspend fun deleteMedia(businessId: String, mediaId: String): Result<Unit> =
        datasource.deleteMedia(businessId, mediaId)
}