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
    val enableButton: Boolean = false,
    val operationType: OperationType = OperationType.Login,
    val createAccountOptionButtonLabel: String,
    val loginOptionButtonLabel: String
)

sealed class OperationType {
    data object Register : OperationType()
    data object Login : OperationType()
}