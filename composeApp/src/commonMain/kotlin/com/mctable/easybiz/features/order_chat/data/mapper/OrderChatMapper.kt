package com.mctable.easybiz.features.order_chat.data.mapper

import com.mctable.easybiz.features.order_chat.data.model.OrderChatMessageResponseModel
import com.mctable.easybiz.features.order_chat.data.model.OrderChatPageResponseModel
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import kotlinx.serialization.json.Json

object OrderChatMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseMessageResponse(jsonString: String): OrderChatMessageResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun parsePageResponse(jsonString: String): OrderChatPageResponseModel {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: OrderChatMessageResponseModel, userId: String): OrderChatMessageEntity {
        return OrderChatMessageEntity(
            id = model.id,
            orderId = model.orderId,
            senderId = model.senderId,
            senderName = model.senderName,
            content = model.content,
            sentAt = model.sentAt,
            isRead = model.isRead,
            readAt = model.readAt,
            senderPhotoUrl = model.senderPhotoUrl,
            mine = userId == model.senderId
        )
    }

    fun toPageEntity(model: OrderChatPageResponseModel, userId: String): OrderChatPageEntity {
        return OrderChatPageEntity(
            messages = model.content.map { toEntity(it, userId) },
            totalElements = model.totalElements,
            totalPages = model.totalPages,
            isLast = model.last
        )
    }
}
