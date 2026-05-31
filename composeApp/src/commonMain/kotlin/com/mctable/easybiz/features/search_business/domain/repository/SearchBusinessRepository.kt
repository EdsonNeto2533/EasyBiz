package com.mctable.easybiz.features.search_business.domain.repository

import com.mctable.easybiz.features.search_business.domain.entity.SearchBusinessPagedEntity

interface SearchBusinessRepository {
    suspend fun getBusiness(
        latitude: Double,
        longitude: Double,
        name: String?,
        page: Int,
        size: Int
    ): Result<SearchBusinessPagedEntity>

    suspend fun addFavorite(businessId: String): Result<Unit>
}
