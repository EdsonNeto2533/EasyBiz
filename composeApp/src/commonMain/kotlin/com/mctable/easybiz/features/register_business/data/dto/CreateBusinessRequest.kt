package com.mctable.easybiz.features.register_business.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBusinessRequest(
    @SerialName("nome")
    val name: String,
    @SerialName("categoria")
    val category: String,
    val latitude: Double,
    val longitude: Double,
    @SerialName("enderecoCompleto")
    val completeAddress: String
)
