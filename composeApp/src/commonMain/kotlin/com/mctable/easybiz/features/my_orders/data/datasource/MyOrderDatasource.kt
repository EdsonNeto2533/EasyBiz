package com.mctable.easybiz.features.my_orders.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.my_orders.data.mapper.MyOrderMapper
import com.mctable.easybiz.features.my_orders.data.model.MyOrderPageResponseModel

interface MyOrderDatasource {
    suspend fun getMyOrders(page: Int, size: Int): Result<MyOrderPageResponseModel>
}

class MyOrderDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : MyOrderDatasource {
    override suspend fun getMyOrders(page: Int, size: Int): Result<MyOrderPageResponseModel> {
        return networking.get(
            host = appEnv.host,
            path = "/pedidos",
            params = mapOf(
                "page" to page.toString(),
                "size" to size.toString()
            ),
            responseMapper = { jsonString ->
                MyOrderMapper.parsePageResponse(jsonString)
            }
        )
    }
}
