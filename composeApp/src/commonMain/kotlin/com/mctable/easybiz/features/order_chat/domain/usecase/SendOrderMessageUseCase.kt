package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository

interface SendOrderMessageUseCase {
    suspend fun execute(orderId: String, content: String): Result<OrderChatMessageEntity>
}

class SendOrderMessageUseCaseImpl(
    private val repository: OrderChatRepository
) : SendOrderMessageUseCase {
    override suspend fun execute(orderId: String, content: String): Result<OrderChatMessageEntity> {
        return repository.sendMessage(orderId, content)
    }
}
