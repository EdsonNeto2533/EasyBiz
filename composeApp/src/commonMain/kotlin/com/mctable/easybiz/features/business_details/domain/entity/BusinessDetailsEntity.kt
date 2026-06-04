package com.mctable.easybiz.features.business_details.domain.entity

data class BusinessDetailsEntity(
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
    val description: String?,
    val telephone: String?,
    val workingHours: String?,
    val minimalValue: Double?,
    val yearsOfExperience: Int?,
    val totalRatings: Int?,
    val isMine: Boolean
)
