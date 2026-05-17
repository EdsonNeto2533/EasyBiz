package com.mctable.easybiz.features.auth.data.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendCodeRequest(
    @SerialName("email")
    val email: String
)
