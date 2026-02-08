package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCase
import com.mctable.easybiz.features.auth.presentation.event.LoginEvent
import com.mctable.easybiz.features.auth.presentation.state.LoginState

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var state by mutableStateOf(initialLoginState())
        private set

    fun onEvent(action: LoginEvent) {
        when (action) {
            LoginEvent.ForgetPasswordClick -> onForgetPasswordClick()
            LoginEvent.LoginClick -> onLoginClick()
            is LoginEvent.OnEmailTyped -> onEmailTyped(action.email)
            is LoginEvent.OnPasswordTyped -> onPasswordTyped(action.password)
        }
    }

    private fun onForgetPasswordClick() {
        TODO("Not yet implemented")
    }

    private fun onLoginClick() {
        TODO("Not yet implemented")
    }

    private fun onEmailTyped(email: String) {
        state = state.copy(email = email)
    }

    private fun onPasswordTyped(password: String) {
        state = state.copy(password = password)
    }


    private fun initialLoginState() = LoginState(
        title = "Bem vindo ao EasyBiz",
        subTitle = "Encontre serviços perto de você de forma simples e rápida",
        inputLabel = "E-mail",
        inputPlaceholder = "ex: easybiz@gmail.com",
        passwordInputLabel = "Senha",
        passwordInputPlaceholder = "Sua senha de acesso",
        forgotPasswordLabel = "Esqueci minha senha",
        loginButtonLabel = "Entrar",
    )

}