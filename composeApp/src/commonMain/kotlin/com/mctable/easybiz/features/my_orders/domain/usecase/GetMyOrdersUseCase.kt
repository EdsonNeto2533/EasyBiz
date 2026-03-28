package com.mctable.easybiz.features.my_orders.domain.usecase

import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.repository.MyOrderRepository

interface GetMyOrdersUseCase{
    suspend fun execute(page: Int, size: Int): Result<MyOrderPageEntity>
}

class GetMyOrdersUseCaseImpl(
    private val repository: MyOrderRepository
): GetMyOrdersUseCase {
    override suspend fun execute(page: Int, size: Int): Result<MyOrderPageEntity> {
        return repository.getMyOrders(page, size)
    }
}
