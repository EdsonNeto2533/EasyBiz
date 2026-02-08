package com.mctable.easybiz.features.auth.presentation.event

sealed class LoginEvent {
    data object LoginClick : LoginEvent()
    data class OnEmailTyped(val email: String) : LoginEvent()
    data class OnPasswordTyped(val password: String) : LoginEvent()
    data object ForgetPasswordClick : LoginEvent()
}
