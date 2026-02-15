package com.mctable.easybiz.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseModel(
    @SerialName("token")
    val token: String,
    @SerialName("user_id")
    val userId: String? = null,
    @SerialName("email")
    val email: String? = null
)
