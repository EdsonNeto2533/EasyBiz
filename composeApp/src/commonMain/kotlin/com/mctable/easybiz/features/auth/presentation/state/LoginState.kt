package com.mctable.easybiz.features.auth.presentation.state

data class LoginState(
    val title: String,
    val subTitle: String,
    val inputLabel: String,
    val inputPlaceholder: String,
    val passwordInputLabel: String,
    val passwordInputPlaceholder: String,
    val forgotPasswordLabel: String,
    val loginButtonLabel: String,
    val email: String? = null,
    val password: String? = null,
    val enableButton: Boolean = false
)