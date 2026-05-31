package com.mctable.easybiz.features.auth.presentation.event

sealed class ForgetPasswordEvent {
    data class OnEmailChanged(val email: String) : ForgetPasswordEvent()
    data object SendEmail : ForgetPasswordEvent()
    data object OnBackClick : ForgetPasswordEvent()
    data object DismissError : ForgetPasswordEvent()
}
