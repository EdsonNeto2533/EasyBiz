package com.mctable.easybiz.features.my_orders.data.repository

import com.mctable.easybiz.features.my_orders.data.datasource.MyOrderDatasource
import com.mctable.easybiz.features.my_orders.data.mapper.MyOrderMapper
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus
import com.mctable.easybiz.features.my_orders.domain.repository.MyOrderRepository

class MyOrderRepositoryImpl(
    private val datasource: MyOrderDatasource
) : MyOrderRepository {
    override suspend fun getMyOrders(
        page: Int,
        size: Int,
        paper: String?,
        businessId: String?
    ): Result<MyOrderPageEntity> = runCatching {
        return datasource.getMyOrders(page, size, paper, businessId).map {
            MyOrderMapper.toPageEntity(it)
        }
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Result<Unit> = runCatching {
        return datasource.updateOrderStatus(orderId, status)
    }
}
