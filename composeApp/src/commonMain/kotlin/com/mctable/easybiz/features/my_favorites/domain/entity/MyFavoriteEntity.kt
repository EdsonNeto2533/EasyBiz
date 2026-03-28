package com.mctable.easybiz.features.my_favorites.domain.entity

data class MyFavoriteEntity(
    val id: String,
    val businessId: String,
    val businessName: String,
    val category: String,
    val averageRating: Double,
    val logoUrl: String?,
    val createdAt: String
)
