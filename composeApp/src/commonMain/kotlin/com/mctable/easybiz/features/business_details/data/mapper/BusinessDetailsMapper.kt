package com.mctable.easybiz.features.business_details.data.mapper

import com.mctable.easybiz.features.business_details.data.model.BusinessDetailsResponseModel
import com.mctable.easybiz.features.business_details.data.model.OrderResponseModel
import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import kotlinx.serialization.json.Json

object BusinessDetailsMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseResponse(jsonString: String): BusinessDetailsResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun parseOrderResponse(jsonString: String): OrderResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: BusinessDetailsResponseModel): BusinessDetailsEntity {
        return BusinessDetailsEntity(
            id = model.id,
            name = model.name,
            category = model.category,
            userId = model.userId,
            userName = model.userName,
            active = model.active,
            latitude = model.latitude,
            longitude = model.longitude,
            completeAddress = model.completeAddress,
            averageRating = model.averageRating,
            logoUrl = model.logoUrl,
            description = model.description,
            minimalValue = model.minimalValue,
            yearsOfExperience = model.yearsOfExperience,
            totalRatings = model.totalRatings
        )
    }
}
