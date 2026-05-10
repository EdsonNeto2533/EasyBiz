package com.mctable.easybiz.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("accessToken")
    val token: String,
    @SerialName("userId")
    val userId: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("nomeCompleto")
    val name: String? = null,
    @SerialName("fotoUrl")
    val photoUrl: String? = null
)