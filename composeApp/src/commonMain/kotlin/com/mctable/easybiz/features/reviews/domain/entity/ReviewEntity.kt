package com.mctable.easybiz.features.reviews.domain.entity

data class ReviewEntity(
    val id: String,
    val reviewerName: String,
    val evaluatedName: String?,
    val rating: Int,
    val comment: String?,
    val createdAt: String,
    val orderId: String?
)