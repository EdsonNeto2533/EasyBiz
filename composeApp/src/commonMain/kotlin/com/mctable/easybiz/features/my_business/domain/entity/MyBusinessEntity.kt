package com.mctable.easybiz.features.my_business.domain.entity

data class MyBusinessEntity(
    val id: Int,
    val name: String,
    val category: String,
    val userId: Int,
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
    val highlightReviewAuthor: String?,
    val isFavorited: Boolean?
)
