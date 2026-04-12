package com.mctable.easybiz.features.my_orders.domain.repository

import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus

interface MyOrderRepository {
    suspend fun getMyOrders(
        page: Int,
        size: Int,
        paper: String?,
        businessId: String?
    ): Result<MyOrderPageEntity>

    suspend fun updateOrderStatus(
        orderId: String,
        status: OrderStatus
    ): Result<Unit>
}
