package com.mctable.easybiz.features.order_chat.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TypingStatusDto(
    @SerialName("usuarioNome") val userName: String,
    @SerialName("digitando") val isTyping: Boolean
)
