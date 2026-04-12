package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository

interface MarkMessageAsReadUseCase {
    suspend fun execute(orderId: String, messageId: String): Result<Unit>
}

class MarkMessageAsReadUseCaseImpl(
    private val repository: OrderChatRepository
) : MarkMessageAsReadUseCase {
    override suspend fun execute(orderId: String, messageId: String): Result<Unit> {
        return repository.markMessageAsRead(orderId, messageId)
    }
}
