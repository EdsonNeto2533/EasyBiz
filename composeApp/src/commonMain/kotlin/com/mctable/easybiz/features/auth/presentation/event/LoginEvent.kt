package com.mctable.easybiz.features.auth.presentation.event

import com.mctable.easybiz.features.auth.presentation.state.OperationType

sealed class LoginEvent {
    data object LoginClick : LoginEvent()
    data class OnEmailTyped(val email: String) : LoginEvent()
    data class OnNameTyped(val name: String) : LoginEvent()
    data class OnPasswordTyped(val password: String) : LoginEvent()
    data object ForgetPasswordClick : LoginEvent()
    data class ChangeOperationType(val currentOperationType: OperationType) : LoginEvent()
    data object HideErrorDialog : LoginEvent()
}
