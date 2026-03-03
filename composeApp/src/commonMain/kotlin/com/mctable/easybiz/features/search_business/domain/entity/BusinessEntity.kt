package com.mctable.easybiz.features.search_business.domain.entity

data class BusinessEntity(
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
    val logo: String,
)
