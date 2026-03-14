package com.mctable.easybiz.features.register_business.presentation.state

data class RegisterBusinessState(
    val businessName: String = "",
    val category: String = "",
    val completeAddress: String = "",
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val success: Boolean = false
)
