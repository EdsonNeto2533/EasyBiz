package com.mctable.easybiz.features.auth.presentation.state

data class ForgetPasswordState(
    val email: String = "",
    val isEmailValid: Boolean = false,
    val showLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null
)
