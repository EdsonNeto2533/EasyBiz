package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.auth.domain.usecase.SendCodeUseCase
import com.mctable.easybiz.features.auth.domain.usecase.VerifyEmailUseCase
import com.mctable.easybiz.features.auth.presentation.event.VerifyEmailEvent
import com.mctable.easybiz.features.auth.presentation.state.VerifyEmailState
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val verifyEmailUseCase: VerifyEmailUseCase,
    private val navigator: Navigator
) : ViewModel() {
    var state by mutableStateOf(initialVerifyEmailState())
        private set

    fun onAction(action: VerifyEmailEvent) {
        when (action) {
            VerifyEmailEvent.HideErrorDialog -> state = state.copy(showErrorDialog = false)
            is VerifyEmailEvent.OnCodeTyped -> state =
                state.copy(
                    code = action.code,
                    enableButton = isValidCode(action.code)
                )

            is VerifyEmailEvent.ConfirmCode -> confirmCode(action.email)
            VerifyEmailEvent.OnBackClick -> navigator.pop()
        }
    }

    private fun confirmCode(email: String) {
        viewModelScope.launch {
            state = state.copy(showLoadingDialog = true)
            state.code?.let {
                verifyEmailUseCase.execute(email, it).fold(
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
        title = "Informe seu código para confirmação",
        subTitle = "Precisamos que informe o código recebido em seu email",
        inputLabel = "Código",
        inputPlaceholder = "0000000",
        emailErrorText = "Insira um código válido",
        buttonText = "Confirmar"
    )

    private fun isValidCode(code: String?): Boolean {
        return code?.length == 6
    }

}