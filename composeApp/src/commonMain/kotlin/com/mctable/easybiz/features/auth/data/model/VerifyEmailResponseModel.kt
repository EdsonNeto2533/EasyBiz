package com.mctable.easybiz.features.auth.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailResponseModel(
    @SerialName("tokenCadastro")
    val registerToken: String
)
