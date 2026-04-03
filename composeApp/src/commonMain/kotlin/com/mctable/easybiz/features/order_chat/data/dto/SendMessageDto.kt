package com.mctable.easybiz.features.order_chat.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SendMessageDto(
    val conteudo: String
)
