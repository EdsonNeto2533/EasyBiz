package com.mctable.easybiz.features.search_business.data.mapper

import com.mctable.easybiz.features.search_business.data.model.BusinessResponseModel
import com.mctable.easybiz.features.search_business.data.model.SearchBusinessPagedResponseModel
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.entity.SearchBusinessPagedEntity
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
            logo = it.logo,
            distance = it.distance,
            favorite = it.favorite ?: false
        )
    }

    fun toDomain(response: SearchBusinessPagedResponseModel) = SearchBusinessPagedEntity(
        content = toDomain(response.content),
        totalElements = response.totalElements,
        totalPages = response.totalPages,
        size = response.size,
        number = response.number
    )

    fun parsePagedResponse(jsonString: String): SearchBusinessPagedResponseModel {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<SearchBusinessPagedResponseModel>(jsonString)
    }
}