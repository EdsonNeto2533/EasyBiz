package com.mctable.easybiz.features.register_business.data.mapper

import com.mctable.easybiz.features.register_business.data.model.BusinessProfileResponseModel
import com.mctable.easybiz.features.register_business.domain.entity.BusinessProfileEntity
import kotlinx.serialization.json.Json

object RegisterBusinessMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseResponse(jsonString: String): BusinessProfileResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: BusinessProfileResponseModel): BusinessProfileEntity {
        return BusinessProfileEntity(
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
            distanceKm = model.distanceKm,
            description = model.description,
            telephone = model.telephone,
            minimumPrice = model.minimumPrice,
            yearsOfExperience = model.yearsOfExperience,
            workingHours = model.workingHours,
            totalReviews = model.totalReviews,
            totalCompletedOrders = model.totalCompletedOrders,
            highlightReview = model.highlightReview,
            highlightReviewAuthor = model.highlightReviewAuthor
        )
    }
}
