package com.mctable.easybiz.features.order_chat.domain.repository

import com.mctable.easybiz.features.order_chat.data.dto.TypingStatusDto
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

    suspend fun sendTypingStatus(
        orderId: String,
        userName: String,
        isTyping: Boolean
    ): Result<Unit>

    suspend fun markMessageAsRead(
        orderId: String,
        messageId: String
    ): Result<Unit>

    suspend fun observeMessages(orderId: String): Flow<OrderChatMessageEntity>

    suspend fun observeTypingStatus(orderId: String): Flow<TypingStatusDto>

    suspend fun observeMessageReadStatus(orderId: String): Flow<String>

    suspend fun disconnect(): Result<Unit>
}
