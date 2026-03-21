package com.mctable.easybiz.features.register_business.presentation.state

data class UpdateBusinessProfileState(
    val description: String = "",
    val cellphone: String = "",
    val minimalPrice: String = "",
    val yearsOfExperience: String = "",
    val image: ByteArray? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val success: Boolean = false,
    val enableButton: Boolean = false
)
