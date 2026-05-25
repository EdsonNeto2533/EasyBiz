package com.mctable.easybiz.features.auth.presentation.event

sealed class VerifyEmailEvent {
    data class ConfirmCode(val email: String, val name: String, val password: String) :
        VerifyEmailEvent()

    data class OnCodeTyped(val code: String) : VerifyEmailEvent()
    data object HideErrorDialog : VerifyEmailEvent()
    data object OnBackClick : VerifyEmailEvent()
}
