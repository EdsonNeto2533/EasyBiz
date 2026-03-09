package com.mctable.easybiz.features.search_business.data.repository

import com.mctable.easybiz.features.search_business.data.datasource.SearchBusinessDatasource
import com.mctable.easybiz.features.search_business.data.mapper.BusinessMapper
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

class SearchBusinessRepositoryImpl(
    val businessDatasource: SearchBusinessDatasource
) : SearchBusinessRepository {

    override suspend fun getBusiness(
        latitude: Double,
        longitude: Double,
        name: String?
    ): Result<List<BusinessEntity>> = runCatching {
        return businessDatasource.searchBusiness(
            latitude,
            longitude,
            name
        ).map { responseModel ->
            BusinessMapper.toDomain(responseModel)
        }
    }
}