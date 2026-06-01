package com.mctable.easybiz.features.search_business.data.repository

import com.mctable.easybiz.features.search_business.data.datasource.SearchBusinessDatasource
import com.mctable.easybiz.features.search_business.data.mapper.BusinessMapper
import com.mctable.easybiz.features.search_business.domain.entity.SearchBusinessPagedEntity
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

class SearchBusinessRepositoryImpl(
    private val businessDatasource: SearchBusinessDatasource
) : SearchBusinessRepository {

    override suspend fun getBusiness(
        latitude: Double,
        longitude: Double,
        name: String?,
        page: Int,
        size: Int
    ): Result<SearchBusinessPagedEntity> = runCatching {
        return businessDatasource.searchBusiness(
            latitude,
            longitude,
            name,
            page,
            size
        ).map { responseModel ->
            BusinessMapper.toDomain(responseModel)
        }
    }

    override suspend fun addFavorite(businessId: String): Result<Unit> = runCatching {
        return businessDatasource.addFavorite(businessId)
    }
}
