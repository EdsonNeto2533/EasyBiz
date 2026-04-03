package com.mctable.easybiz.features.business_details.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.business_details.data.dto.CreateOrderRequest
import com.mctable.easybiz.features.business_details.data.mapper.BusinessDetailsMapper
import com.mctable.easybiz.features.business_details.data.model.BusinessDetailsResponseModel
import com.mctable.easybiz.features.business_details.data.model.OrderResponseModel

interface BusinessDetailsDatasource {
    suspend fun getBusinessDetails(id: String): Result<BusinessDetailsResponseModel>
    suspend fun createOrder(request: CreateOrderRequest): Result<OrderResponseModel>
}

class BusinessDetailsDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : BusinessDetailsDatasource {
    override suspend fun getBusinessDetails(id: String): Result<BusinessDetailsResponseModel> {
        return networking.get(
            host = appEnv.host,
            path = "/negocios/$id",
            responseMapper = { jsonString ->
                BusinessDetailsMapper.parseResponse(jsonString)
            }
        )
    }

    override suspend fun createOrder(request: CreateOrderRequest): Result<OrderResponseModel> {
        return networking.post(
            host = appEnv.host,
            path = "/pedidos",
            body = request,
            responseMapper = { jsonString ->
                BusinessDetailsMapper.parseOrderResponse(jsonString)
            }
        )
    }
}
