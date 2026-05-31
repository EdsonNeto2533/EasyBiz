package com.mctable.easybiz.features.auth.presentation.event

sealed class ResetPasswordEvent {
    data class SetEmail(val email: String) : ResetPasswordEvent()
    data class OnTokenChanged(val token: String) : ResetPasswordEvent()
    data class OnNewPasswordChanged(val password: String) : ResetPasswordEvent()
    data class OnConfirmPasswordChanged(val password: String) : ResetPasswordEvent()
    data object ResetPassword : ResetPasswordEvent()
    data object OnBackClick : ResetPasswordEvent()
    data object DismissError : ResetPasswordEvent()
    data object OnSuccessDismiss : ResetPasswordEvent()
}
