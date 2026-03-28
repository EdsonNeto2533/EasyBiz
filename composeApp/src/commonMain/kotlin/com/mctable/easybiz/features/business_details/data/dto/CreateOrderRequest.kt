package com.mctable.easybiz.features.business_details.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderRequest(
    @SerialName("negocioId")
    val businessId: String,
    @SerialName("descricao")
    val description: String,
    @SerialName("dataDesejada")
    val desiredDate: String
)
