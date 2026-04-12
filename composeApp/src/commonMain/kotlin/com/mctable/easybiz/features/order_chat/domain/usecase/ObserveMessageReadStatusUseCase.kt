package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import kotlinx.coroutines.flow.Flow

interface ObserveMessageReadStatusUseCase {
    suspend fun execute(orderId: String): Flow<String>
}

class ObserveMessageReadStatusUseCaseImpl(
    private val repository: OrderChatRepository
) : ObserveMessageReadStatusUseCase {
    override suspend fun execute(orderId: String): Flow<String> {
        return repository.observeMessageReadStatus(orderId)
    }
}
