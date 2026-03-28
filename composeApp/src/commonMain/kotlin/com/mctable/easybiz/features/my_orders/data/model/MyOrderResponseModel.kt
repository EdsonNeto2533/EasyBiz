package com.mctable.easybiz.features.my_orders.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyOrderResponseModel(
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

@Serializable
data class MyOrderPageResponseModel(
    val content: List<MyOrderResponseModel>,
    val totalElements: Int,
    val totalPages: Int,
    @SerialName("last") val isLast: Boolean
)
