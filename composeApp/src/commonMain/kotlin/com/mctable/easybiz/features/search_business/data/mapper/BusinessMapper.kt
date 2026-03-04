package com.mctable.easybiz.features.search_business.data.mapper

import com.mctable.easybiz.features.search_business.data.model.BusinessResponseModel
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import kotlinx.serialization.json.Json

object BusinessMapper {

    fun toDomain(response: List<BusinessResponseModel>) = response.map {
        BusinessEntity(
            id = it.id,
            name = it.name,
            category = it.category,
            userId = it.userId,
            userName = it.userName,
            active = it.active,
            latitude = it.latitude,
            longitude = it.longitude,
            completeAddress = it.completeAddress,
            averageRating = it.averageRating,
            logo = it.logo
        )
    }

    fun parseResponse(jsonString: String): List<BusinessResponseModel> {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<List<BusinessResponseModel>>(jsonString)
    }
}