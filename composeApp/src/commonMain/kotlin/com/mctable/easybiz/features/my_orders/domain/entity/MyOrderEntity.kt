package com.mctable.easybiz.features.my_orders.domain.entity

data class MyOrderEntity(
    val id: String,
    val clientId: String,
    val clientName: String,
    val businessId: String,
    val businessName: String,
    val description: String,
    val desiredDate: String,
    val status: String,
    val createdAt: String,
    val businessLogoUrl: String?
)

data class MyOrderPageEntity(
    val content: List<MyOrderEntity>,
    val totalElements: Int,
    val totalPages: Int,
    val isLast: Boolean
)
