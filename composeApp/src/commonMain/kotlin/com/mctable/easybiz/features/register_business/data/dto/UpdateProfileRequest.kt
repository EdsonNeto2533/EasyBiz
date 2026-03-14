package com.mctable.easybiz.features.register_business.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequest(
    @SerialName("descricao")
    val description: String,
    @SerialName("telefone")
    val telephone: String,
    @SerialName("precoMinimo")
    val minimumPrice: Double,
    @SerialName("anosExperiencia")
    val yearsOfExperience: Int,
    @SerialName("horarioFuncionamento")
    val workingHours: String
)
