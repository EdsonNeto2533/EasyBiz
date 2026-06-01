package com.mctable.easybiz.features.reviews.domain.repository

import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity

interface ReviewRepository {
    suspend fun getBusinessReviews(businessId: String): Result<List<ReviewEntity>>
    suspend fun submitReview(orderId: String, rating: Int, comment: String?): Result<Unit>
}