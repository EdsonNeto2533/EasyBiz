package com.mctable.easybiz.features.my_business.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyBusinessResponseModel(
    val id: Int,
    @SerialName("nome") val name: String,
    @SerialName("categoria") val category: String,
    @SerialName("usuarioId") val userId: Int,
    @SerialName("nomeUsuario") val userName: String,
    @SerialName("ativo") val active: Boolean,
    val latitude: Double,
    val longitude: Double,
    @SerialName("enderecoCompleto") val completeAddress: String,
    @SerialName("notaMedia") val averageRating: Double,
    val logoUrl: String? = null,
    @SerialName("distanciaKm") val distanceKm: Double,
    @SerialName("descricao") val description: String? = null,
    @SerialName("telefone") val telephone: String? = null,
    @SerialName("precoMinimo") val minimumPrice: Double? = null,
    @SerialName("anosExperiencia") val yearsOfExperience: Int? = null,
    @SerialName("horarioFuncionamento") val workingHours: String? = null,
    val totalReviews: Int? = null,
    val totalCompletedOrders: Int? = null,
    @SerialName("avaliacaoDestaque") val highlightReview: String? = null,
    @SerialName("avaliacaoDestaqueAutor") val highlightReviewAuthor: String? = null,
    val isFavoritado: Boolean
)
