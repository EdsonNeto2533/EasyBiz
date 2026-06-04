package com.mctable.easybiz.features.register_business.presentation.state

data class UpdateBusinessProfileState(
    val description: String = "",
    val cellphone: String = "",
    val workingHours: String = "",
    val minimalPrice: String = "",
    val yearsOfExperience: String = "",
    val logoUrl: String? = null,
    val image: ByteArray? = null,
    val isLoadingData: Boolean = false,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val success: Boolean = false,
    val enableButton: Boolean = false,
    val errorMessage: String? = null
)
