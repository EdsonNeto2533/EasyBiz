package com.mctable.easybiz.features.search_business.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusinessResponseModel(
    val id: Int,
    @SerialName("nome")
    val name: String,
    @SerialName("categoria")
    val category: String,
    @SerialName("usuarioId")
    val userId: Int,
    @SerialName("nomeUsuario")
    val userName: String,
    @SerialName("ativo")
    val active: Boolean,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("enderecoCompleto")
    val completeAddress: String,
    @SerialName("notaMedia")
    val averageRating: Double,
    @SerialName("logoUrl")
    val logo: String,
)