package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import kotlinx.coroutines.flow.Flow

interface ObserveOrderMessagesUseCase {
    suspend fun execute(orderId: String): Flow<OrderChatMessageEntity>
}

class ObserveOrderMessagesUseCaseImpl(
    private val repository: OrderChatRepository
) : ObserveOrderMessagesUseCase {
    override suspend fun execute(orderId: String): Flow<OrderChatMessageEntity> {
        return repository.observeMessages(orderId)
    }
}
