package com.mctable.easybiz.features.register_business.domain.entity

data class BusinessProfileEntity(
    val id: String,
    val name: String,
    val category: String,
    val userId: String,
    val userName: String,
    val active: Boolean,
    val latitude: Double,
    val longitude: Double,
    val completeAddress: String,
    val averageRating: Double,
    val logoUrl: String?,
    val distanceKm: Double?,
    val description: String?,
    val telephone: String?,
    val minimumPrice: Double?,
    val yearsOfExperience: Int?,
    val workingHours: String?,
    val totalReviews: Int?,
    val totalCompletedOrders: Int?,
    val highlightReview: String?,
    val highlightReviewAuthor: String?
)
