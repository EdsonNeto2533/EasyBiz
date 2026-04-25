package com.mctable.easybiz.features.order_chat.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderChatMessageResponseModel(
    val id: String,
    @SerialName("pedidoServicoId") val orderId: String,
    @SerialName("remetenteId") val senderId: String,
    @SerialName("remetenteNome") val senderName: String,
    @SerialName("conteudo") val content: String,
    @SerialName("enviadoEm") val sentAt: String,
    @SerialName("lida") val isRead: Boolean,
    @SerialName("lidaEm") val readAt: String? = null,
    @SerialName("remetenteFotoUrl") val senderPhotoUrl: String? = null
)

@Serializable
data class OrderChatPageResponseModel(
    val content: List<OrderChatMessageResponseModel>,
    val totalElements: Int,
    val totalPages: Int,
    val last: Boolean
)
