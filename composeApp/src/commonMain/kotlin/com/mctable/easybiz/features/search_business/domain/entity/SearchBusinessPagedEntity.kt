package com.mctable.easybiz.features.search_business.domain.entity

data class SearchBusinessPagedEntity(
    val content: List<BusinessEntity>,
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int
)
