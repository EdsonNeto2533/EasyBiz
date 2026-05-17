package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.auth.domain.usecase.SendCodeUseCase
import com.mctable.easybiz.features.auth.presentation.event.VerifyEmailEvent
import com.mctable.easybiz.features.auth.presentation.state.VerifyEmailState
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val sendCodeUseCase: SendCodeUseCase,
    private val navigator: Navigator
) : ViewModel() {
    var state by mutableStateOf(initialVerifyEmailState())
        private set

    fun onAction(action: VerifyEmailEvent) {
        when (action) {
            VerifyEmailEvent.HideErrorDialog -> state = state.copy(showErrorDialog = false)
            is VerifyEmailEvent.OnEmailTyped -> state =
                state.copy(
                    email = action.email,
                    enableButton = isValidEmail(action.email)
                )

            VerifyEmailEvent.SendCode -> sendCode()
            VerifyEmailEvent.OnBackClick -> navigator.pop()
        }
    }

    private fun sendCode() {
        viewModelScope.launch {
            state = state.copy(showLoadingDialog = true)
            state.email?.let {
                sendCodeUseCase.execute(it).fold(
                    ::handleSendCodeSuccess,
                    ::handleSendCodeError
                )
            }

        }

    }

    private fun handleSendCodeSuccess(unit: Unit) {
        state = state.copy(showLoadingDialog = false)
    }

    private fun handleSendCodeError(failure: Throwable) {
        state = state.copy(showErrorDialog = true, showLoadingDialog = false)
        //todo navigate
    }


    private fun initialVerifyEmailState() = VerifyEmailState(
        title = "Informe seu email para confirmação",
        subTitle = "Precisamos que informe seu email para o envio do código de confirmação",
        inputLabel = "E-mail",
        inputPlaceholder = "ex: easybiz@gmail.com",
        emailErrorText = "Insira um email válido",
        buttonText = "enviar código"
    )

    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email?.matches(emailRegex.toRegex()) ?: false
    }

}