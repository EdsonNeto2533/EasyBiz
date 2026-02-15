package com.mctable.easybiz.features.auth.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val email: String,
    @SerialName("senha")
    val password: String,
    @SerialName("nomeCompleto")
    val name: String
)
