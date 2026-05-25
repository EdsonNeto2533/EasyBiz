package com.mctable.easybiz.features.auth.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailRequest(
    @SerialName("email")
    val email: String,
    @SerialName("codigo")
    val code: String
)
