package com.mctable.easybiz.features.user_data.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDataResponseModel(
    val id: String,
    @SerialName("nome")
    val name: String,
    val email: String,
    @SerialName("fotoUrl")
    val photoUrl: String? = null
)
