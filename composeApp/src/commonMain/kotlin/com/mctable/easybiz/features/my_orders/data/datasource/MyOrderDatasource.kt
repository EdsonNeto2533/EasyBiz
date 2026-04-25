package com.mctable.easybiz.features.my_orders.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.my_orders.data.mapper.MyOrderMapper
import com.mctable.easybiz.features.my_orders.data.model.MyOrderPageResponseModel
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus

interface MyOrderDatasource {
    suspend fun getMyOrders(
        page: Int,
        size: Int,
        paper: String?,
        businessId: String?
    ): Result<MyOrderPageResponseModel>

    suspend fun updateOrderStatus(
        orderId: String,
        status: OrderStatus
    ): Result<Unit>
}

class MyOrderDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : MyOrderDatasource {
    override suspend fun getMyOrders(
        page: Int,
        size: Int,
        paper: String?,
        businessId: String?
    ): Result<MyOrderPageResponseModel> {
        val params = mutableMapOf(
            "page" to page.toString(),
            "size" to size.toString()
        )

        paper?.let { params["papel"] = it }
        businessId?.let { params["negocioId"] = it }

        return networking.get(
            host = appEnv.host,
            path = "/pedidos",
            params = params,
            responseMapper = { jsonString ->
                MyOrderMapper.parsePageResponse(jsonString)
            }
        )
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Unit> {
        val path = when (status) {
            OrderStatus.ACEITO -> "/pedidos/$orderId/ACEITAR"
            OrderStatus.CONCLUIDO -> "/pedidos/$orderId/CONCLUIR"
            OrderStatus.CANCELADO -> "/pedidos/$orderId/CANCELAR"
            OrderStatus.RECUSADO -> "/pedidos/$orderId/RECUSAR"
            else -> throw IllegalArgumentException("Invalid status update")
        }

        return networking.patch(
            host = appEnv.host,
            path = path,
            responseMapper = { }
        )
    }
}
