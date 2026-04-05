package com.mctable.easybiz.features.order_chat.domain.repository

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import kotlinx.coroutines.flow.Flow

interface OrderChatRepository {
    suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageEntity>

    suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<Unit>

    suspend fun observeMessages(orderId: String): Flow<OrderChatMessageEntity>
}
