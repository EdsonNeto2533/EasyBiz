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
    val name: String? = null,
    val enableButton: Boolean = false,
    val operationType: OperationType = OperationType.Login,
    val createAccountOptionButtonLabel: String,
    val loginOptionButtonLabel: String,
    val nameErrorText: String? = null,
    val emailErrorText: String? = null,
    val passwordErrorText: String? = null,
    val showNameError: Boolean = false,
    val showEmailError: Boolean = false,
    val showPasswordError: Boolean = false,
    val showErrorDialog: Boolean = false,
    val showLoadingDialog: Boolean = false,
    val showPermissionDeniedScreen: Boolean = false,
    val showPermissionDeniedForeverScreen: Boolean = false
)

sealed class OperationType {
    data object Register : OperationType()
    data object Login : OperationType()
}