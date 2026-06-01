package com.mctable.easybiz.features.reviews.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseModel(
    val id: String,
    @SerialName("avaliadorNome") val reviewerName: String,
    @SerialName("avaliadoNome") val evaluatedName: String? = null,
    @SerialName("nota") val rating: Int,
    @SerialName("comentario") val comment: String? = null,
    @SerialName("dataAvaliacao") val createdAt: String,
    @SerialName("pedidoId") val orderId: String? = null
)