package com.mctable.easybiz.features.my_favorites.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyFavoriteResponseModel(
    val id: Int,
    @SerialName("negocioId") val businessId: Int,
    @SerialName("negocioNome") val businessName: String,
    @SerialName("negocioCategoria") val category: String,
    @SerialName("notaMedia") val averageRating: Double,
    val logoUrl: String? = null,
    @SerialName("criadoEm") val createdAt: String
)
