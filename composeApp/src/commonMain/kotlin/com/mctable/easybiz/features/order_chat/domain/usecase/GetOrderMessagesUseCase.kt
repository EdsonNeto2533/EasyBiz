package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository

interface GetOrderMessagesUseCase {
    suspend fun execute(orderId: String, page: Int, size: Int): Result<OrderChatPageEntity>
}

class GetOrderMessagesUseCaseImpl(
    private val repository: OrderChatRepository
) : GetOrderMessagesUseCase {
    override suspend fun execute(orderId: String, page: Int, size: Int): Result<OrderChatPageEntity> {
        return repository.getMessages(orderId, page, size)
    }
}
