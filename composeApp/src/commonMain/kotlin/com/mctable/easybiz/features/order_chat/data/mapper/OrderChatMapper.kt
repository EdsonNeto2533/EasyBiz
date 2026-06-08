package com.mctable.easybiz.features.order_chat.data.mapper

import com.mctable.easybiz.features.order_chat.data.model.OrderChatMessageResponseModel
import com.mctable.easybiz.features.order_chat.data.model.OrderChatPageResponseModel
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
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
            sentAt = model.sentAt.utcToLocal(),
            isRead = model.isRead,
            readAt = model.readAt?.utcToLocal(),
            senderPhotoUrl = model.senderPhotoUrl,
            mine = userId == model.senderId
        )
    }

    fun toPageEntity(model: OrderChatPageResponseModel, userId: String): OrderChatPageEntity {
        return OrderChatPageEntity(
            messages = model.content.map { toEntity(it, userId) },
            totalElements = model.totalElements,
            totalPages = model.totalPages,
            isLast = model.number >= model.totalPages - 1
        )
    }

    // Servidor envia UTC sem sufixo ("2026-06-08T16:19:00") — converte para fuso do device
    private fun String.utcToLocal(): String = try {
        val local = LocalDateTime.parse(this)
            .toInstant(TimeZone.UTC)
            .toLocalDateTime(TimeZone.currentSystemDefault())
        local.toString()
    } catch (_: Exception) {
        this
    }
}
