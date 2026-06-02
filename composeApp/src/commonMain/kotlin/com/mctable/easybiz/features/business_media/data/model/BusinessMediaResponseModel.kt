package com.mctable.easybiz.features.business_media.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusinessMediaResponseModel(
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("tipo")
    val tipo: String,
    @SerialName("criadoEm")
    val criadoEm: String? = null
)