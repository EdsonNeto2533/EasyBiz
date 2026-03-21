package com.mctable.easybiz.features.my_orders.data.repository

import com.mctable.easybiz.features.my_orders.data.datasource.MyOrderDatasource
import com.mctable.easybiz.features.my_orders.data.mapper.MyOrderMapper
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.repository.MyOrderRepository

class MyOrderRepositoryImpl(
    private val datasource: MyOrderDatasource
) : MyOrderRepository {
    override suspend fun getMyOrders(page: Int, size: Int): Result<MyOrderPageEntity>  = runCatching {
        return datasource.getMyOrders(page, size).map {
            MyOrderMapper.toPageEntity(it)
        }
    }
}
