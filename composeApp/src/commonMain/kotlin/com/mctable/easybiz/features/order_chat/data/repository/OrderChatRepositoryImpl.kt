package com.mctable.easybiz.features.order_chat.data.repository

import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasource
import com.mctable.easybiz.features.order_chat.data.mapper.OrderChatMapper
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession

class OrderChatRepositoryImpl(
    private val datasource: OrderChatDatasource
) : OrderChatRepository {

    override suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageEntity> = runCatching {
        val response = datasource.getMessages(orderId, page, size).getOrThrow()
        OrderChatMapper.toPageEntity(response)
    }

    override suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<OrderChatMessageEntity> = runCatching {
        val response = datasource.sendMessage(orderId, content).getOrThrow()
        OrderChatMapper.toEntity(response)
    }

    override suspend fun connectToChat(
        orderId: String,
        block: suspend DefaultClientWebSocketSession.() -> Unit
    ): Result<Unit> = runCatching {
        datasource.connectToChat(orderId, block).getOrThrow()
    }
}
