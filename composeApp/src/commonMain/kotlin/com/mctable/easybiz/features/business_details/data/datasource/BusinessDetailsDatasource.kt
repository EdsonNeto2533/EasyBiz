package com.mctable.easybiz.features.business_details.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.business_details.data.mapper.BusinessDetailsMapper
import com.mctable.easybiz.features.business_details.data.model.BusinessDetailsResponseModel

interface BusinessDetailsDatasource {
    suspend fun getBusinessDetails(id: Int): Result<BusinessDetailsResponseModel>
}

class BusinessDetailsDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : BusinessDetailsDatasource {
    override suspend fun getBusinessDetails(id: Int): Result<BusinessDetailsResponseModel> {
        return networking.get(
            host = appEnv.host,
            path = "/negocios/$id",
            responseMapper = { jsonString ->
                BusinessDetailsMapper.parseResponse(jsonString)
            }
        )
    }
}
