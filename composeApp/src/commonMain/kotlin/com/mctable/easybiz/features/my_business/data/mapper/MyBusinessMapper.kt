package com.mctable.easybiz.features.my_business.data.mapper

import com.mctable.easybiz.features.my_business.data.model.MyBusinessResponseModel
import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity
import kotlinx.serialization.json.Json

object MyBusinessMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseListResponse(jsonString: String): List<MyBusinessResponseModel> {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: MyBusinessResponseModel): MyBusinessEntity {
        return MyBusinessEntity(
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
            highlightReviewAuthor = model.highlightReviewAuthor,
            isFavorited = model.isFavoritado
        )
    }
}
