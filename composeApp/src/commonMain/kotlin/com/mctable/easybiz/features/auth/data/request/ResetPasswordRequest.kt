package com.mctable.easybiz.features.auth.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordRequest(
    val email: String,
    val token: String,
    @SerialName("novaSenha")
    val newPassword: String
)
