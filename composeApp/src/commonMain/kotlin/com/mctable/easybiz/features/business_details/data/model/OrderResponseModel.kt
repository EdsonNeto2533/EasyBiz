package com.mctable.easybiz.features.business_details.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderResponseModel(
    val id: String,
    @SerialName("clienteId") val clientId: String,
    @SerialName("clienteNome") val clientName: String,
    @SerialName("negocioId") val businessId: String,
    @SerialName("negocioNome") val businessName: String,
    @SerialName("descricao") val description: String,
    @SerialName("dataDesejada") val desiredDate: String,
    val status: String,
    @SerialName("criadoEm") val createdAt: String,
    @SerialName("negocioLogoUrl") val businessLogoUrl: String? = null
)
