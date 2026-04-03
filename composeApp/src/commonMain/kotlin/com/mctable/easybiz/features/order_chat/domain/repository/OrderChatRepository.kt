package com.mctable.easybiz.features.order_chat.domain.repository

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession

interface OrderChatRepository {
    suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageEntity>

    suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<OrderChatMessageEntity>

    suspend fun connectToChat(
        orderId: String,
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit>
}
