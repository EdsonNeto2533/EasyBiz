package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository

interface SendTypingStatusUseCase {
    suspend fun execute(orderId: String, userName: String, isTyping: Boolean): Result<Unit>
}

class SendTypingStatusUseCaseImpl(
    private val repository: OrderChatRepository
) : SendTypingStatusUseCase {
    override suspend fun execute(orderId: String, userName: String, isTyping: Boolean): Result<Unit> {
        return repository.sendTypingStatus(orderId, userName, isTyping)
    }
}
