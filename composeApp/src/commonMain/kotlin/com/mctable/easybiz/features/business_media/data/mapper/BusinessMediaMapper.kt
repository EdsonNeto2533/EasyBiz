package com.mctable.easybiz.features.business_media.data.mapper

import com.mctable.easybiz.features.business_media.data.model.BusinessMediaResponseModel
import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity
import kotlinx.serialization.json.Json

object BusinessMediaMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseListResponse(jsonString: String): List<BusinessMediaResponseModel> =
        json.decodeFromString(jsonString)

    fun toEntity(model: BusinessMediaResponseModel) = BusinessMediaEntity(
        id = model.id,
        url = model.url,
        type = model.type,
        createdAt = model.createdAt
    )
}