package com.mctable.easybiz.features.auth.presentation.state

data class ResetPasswordState(
    val email: String = "",
    val token: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val isFormValid: Boolean = false,
    val showLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)
