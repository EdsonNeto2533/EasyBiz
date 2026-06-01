package com.mctable.easybiz.features.reviews.data.mapper

import com.mctable.easybiz.features.reviews.data.model.ReviewResponseModel
import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity
import kotlinx.serialization.json.Json

object ReviewMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseListResponse(jsonString: String): List<ReviewResponseModel> {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: ReviewResponseModel): ReviewEntity {
        return ReviewEntity(
            id = model.id,
            reviewerName = model.reviewerName,
            evaluatedName = model.evaluatedName,
            rating = model.rating,
            comment = model.comment,
            createdAt = model.createdAt,
            orderId = model.orderId
        )
    }
}