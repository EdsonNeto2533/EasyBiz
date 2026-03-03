package com.mctable.easybiz.features.search_business.data.repository

import com.mctable.easybiz.features.search_business.data.datasource.SearchBusinessDatasource
import com.mctable.easybiz.features.search_business.data.mapper.BusinessMapper
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

class SearchBusinessRepositoryImpl(
    val businessDatasource: SearchBusinessDatasource
) : SearchBusinessRepository {

    override suspend fun getBusiness(): Result<List<BusinessEntity>> = runCatching {
        return businessDatasource.searchBusiness().mapCatching { responseModel ->
            BusinessMapper.toDomain(responseModel)
        }
    }
}