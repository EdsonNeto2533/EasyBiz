package com.mctable.easybiz.features.search_business.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.search_business.data.mapper.BusinessMapper
import com.mctable.easybiz.features.search_business.data.model.BusinessResponseModel

interface SearchBusinessDatasource {
    suspend fun searchBusiness(): Result<List<BusinessResponseModel>>

}

class SearchBusinessDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : SearchBusinessDatasource {

    override suspend fun searchBusiness(): Result<List<BusinessResponseModel>> {
        return networking.get(
            host = appEnv.host,
            path = "/negocios/busca",
            responseMapper = { jsonString ->
                BusinessMapper.parseResponse(jsonString)
            }
        )
    }

}