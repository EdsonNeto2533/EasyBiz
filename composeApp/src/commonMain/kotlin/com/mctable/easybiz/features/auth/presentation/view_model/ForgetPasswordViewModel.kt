package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.auth.domain.usecase.ForgetPasswordUseCase
import com.mctable.easybiz.features.auth.presentation.event.ForgetPasswordEvent
import com.mctable.easybiz.features.auth.presentation.state.ForgetPasswordState
import kotlinx.coroutines.launch

class ForgetPasswordViewModel(
    private val forgetPasswordUseCase: ForgetPasswordUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ForgetPasswordState())
        private set

    fun onAction(event: ForgetPasswordEvent) {
        when (event) {
            is ForgetPasswordEvent.OnEmailChanged -> {
                state = state.copy(
                    email = event.email,
                    isEmailValid = isValidEmail(event.email)
                )
            }
            ForgetPasswordEvent.SendEmail -> sendForgetPasswordEmail()
            ForgetPasswordEvent.OnBackClick -> navigator.pop()
            ForgetPasswordEvent.DismissError -> state = state.copy(showErrorDialog = false)
        }
    }

    private fun sendForgetPasswordEmail() {
        viewModelScope.launch {
            state = state.copy(showLoading = true)
            forgetPasswordUseCase.execute(state.email)
                .onSuccess {
                    state = state.copy(showLoading = false)
                    navigator.navigate(Destination.ResetPassword(state.email))
                }
                .onFailure {
                    state = state.copy(
                        showLoading = false,
                        showErrorDialog = true,
                        errorMessage = it.message ?: "Ocorreu um erro ao enviar o e-mail"
                    )
                }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }
}
