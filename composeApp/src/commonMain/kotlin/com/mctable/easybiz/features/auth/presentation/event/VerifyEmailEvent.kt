package com.mctable.easybiz.features.auth.presentation.event

sealed class VerifyEmailEvent {
    data object SendCode : VerifyEmailEvent()
    data class OnEmailTyped(val email: String) : VerifyEmailEvent()
    data object HideErrorDialog : VerifyEmailEvent()
    data object OnBackClick: VerifyEmailEvent()
}
