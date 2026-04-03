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

    fun toEntity(model: OrderChatMessageResponseModel): OrderChatMessageEntity {
        return OrderChatMessageEntity(
            id = model.id,
            orderId = model.orderId,
            senderId = model.senderId,
            senderName = model.senderName,
            content = model.content,
            sentAt = model.sentAt,
            isRead = model.isRead,
            readAt = model.readAt,
            senderPhotoUrl = model.senderPhotoUrl
        )
    }

    fun toPageEntity(model: OrderChatPageResponseModel): OrderChatPageEntity {
        return OrderChatPageEntity(
            messages = model.content.map { toEntity(it) },
            totalElements = model.totalElements,
            totalPages = model.totalPages,
            isLast = model.last
        )
    }
}
