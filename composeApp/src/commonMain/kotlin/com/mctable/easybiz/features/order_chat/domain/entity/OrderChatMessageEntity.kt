package com.mctable.easybiz.features.order_chat.domain.entity

data class OrderChatMessageEntity(
    val id: String,
    val orderId: String,
    val senderId: String,
    val senderName: String,
    val content: String,
    val sentAt: String,
    val isRead: Boolean,
    val readAt: String?,
    val senderPhotoUrl: String?,
    val mine: Boolean
)

data class OrderChatPageEntity(
    val messages: List<OrderChatMessageEntity>,
    val totalElements: Int,
    val totalPages: Int,
    val isLast: Boolean
)
