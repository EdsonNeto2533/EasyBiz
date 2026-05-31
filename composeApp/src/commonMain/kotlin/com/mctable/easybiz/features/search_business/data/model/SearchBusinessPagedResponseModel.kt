package com.mctable.easybiz.features.search_business.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchBusinessPagedResponseModel(
    val content: List<BusinessResponseModel>,
    val totalElements: Int,
    val totalPages: Int,
    val size: Int,
    val number: Int
)
