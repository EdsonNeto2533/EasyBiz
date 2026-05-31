package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.auth.data.request.ResetPasswordRequest
import com.mctable.easybiz.features.auth.domain.usecase.ResetPasswordUseCase
import com.mctable.easybiz.features.auth.presentation.event.ResetPasswordEvent
import com.mctable.easybiz.features.auth.presentation.state.ResetPasswordState
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ResetPasswordState())
        private set

    fun onAction(event: ResetPasswordEvent) {
        when (event) {
            is ResetPasswordEvent.SetEmail -> {
                state = state.copy(email = event.email)
            }
            is ResetPasswordEvent.OnTokenChanged -> {
                state = state.copy(token = event.token)
                validateForm()
            }
            is ResetPasswordEvent.OnNewPasswordChanged -> {
                state = state.copy(newPassword = event.password)
                validateForm()
            }
            is ResetPasswordEvent.OnConfirmPasswordChanged -> {
                state = state.copy(confirmPassword = event.password)
                validateForm()
            }
            ResetPasswordEvent.ResetPassword -> performResetPassword()
            ResetPasswordEvent.OnBackClick -> navigator.pop()
            ResetPasswordEvent.DismissError -> state = state.copy(showErrorDialog = false)
            ResetPasswordEvent.OnSuccessDismiss -> {
                state = state.copy(isSuccess = false)
                navigator.navigate(Destination.Login, clearBackStack = true)
            }
        }
    }

    private fun validateForm() {
        val isValid = state.token.isNotBlank() &&
                state.newPassword.isNotBlank() &&
                state.newPassword == state.confirmPassword &&
                state.newPassword.length >= 6
        state = state.copy(isFormValid = isValid)
    }

    private fun performResetPassword() {
        viewModelScope.launch {
            state = state.copy(showLoading = true)
            val request = ResetPasswordRequest(
                email = state.email,
                token = state.token,
                newPassword = state.newPassword
            )
            resetPasswordUseCase.execute(request)
                .onSuccess {
                    state = state.copy(showLoading = false, isSuccess = true)
                }
                .onFailure {
                    state = state.copy(
                        showLoading = false,
                        showErrorDialog = true,
                        errorMessage = it.message ?: "Erro ao redefinir senha"
                    )
                }
        }
    }
}
