package com.mctable.easybiz.features.my_orders.domain.usecase

import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus
import com.mctable.easybiz.features.my_orders.domain.repository.MyOrderRepository

interface UpdateOrderStatusUseCase {
    suspend fun execute(orderId: String, status: OrderStatus): Result<Unit>
}

class UpdateOrderStatusUseCaseImpl(
    private val repository: MyOrderRepository
) : UpdateOrderStatusUseCase {
    override suspend fun execute(orderId: String, status: OrderStatus): Result<Unit> {
        return repository.updateOrderStatus(orderId, status)
    }
}
