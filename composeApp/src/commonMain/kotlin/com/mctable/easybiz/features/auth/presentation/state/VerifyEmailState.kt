package com.mctable.easybiz.features.auth.presentation.state

data class VerifyEmailState(
    val title: String,
    val subTitle: String,
    val inputLabel: String,
    val inputPlaceholder: String,
    val buttonText: String,
    val email: String? = null,
    val enableButton: Boolean = false,
    val emailErrorText: String? = null,
    val showEmailError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val showLoadingDialog: Boolean = false,
)