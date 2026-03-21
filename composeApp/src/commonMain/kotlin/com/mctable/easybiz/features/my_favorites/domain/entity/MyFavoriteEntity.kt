package com.mctable.easybiz.features.my_favorites.domain.entity

data class MyFavoriteEntity(
    val id: Int,
    val businessId: Int,
    val businessName: String,
    val category: String,
    val averageRating: Double,
    val logoUrl: String?,
    val createdAt: String
)
