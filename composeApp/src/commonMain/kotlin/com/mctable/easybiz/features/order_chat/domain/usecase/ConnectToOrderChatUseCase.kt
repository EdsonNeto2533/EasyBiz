package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession

interface ConnectToOrderChatUseCase {
    suspend fun execute(
        orderId: String,
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit>
}

class ConnectToOrderChatUseCaseImpl(
    private val repository: OrderChatRepository
) : ConnectToOrderChatUseCase {
    override suspend fun execute(
        orderId: String,
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit> {
        return repository.connectToChat(orderId, block)
    }
}
