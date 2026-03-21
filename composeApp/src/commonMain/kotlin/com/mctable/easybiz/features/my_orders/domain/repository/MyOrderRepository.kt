package com.mctable.easybiz.features.my_orders.domain.repository

import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity

interface MyOrderRepository {
    suspend fun getMyOrders(page: Int, size: Int): Result<MyOrderPageEntity>
}
