package com.mctable.easybiz.features.user_data.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    @SerialName("nomeCompleto")
    val fullName: String
)
