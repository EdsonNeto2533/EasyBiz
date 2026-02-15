package com.mctable.easybiz.features.auth.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCase
import com.mctable.easybiz.features.auth.presentation.event.LoginEvent
import com.mctable.easybiz.features.auth.presentation.state.LoginState
import com.mctable.easybiz.features.auth.presentation.state.OperationType
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(initialLoginState())
        private set

    fun onEvent(action: LoginEvent) {
        when (action) {
            LoginEvent.ForgetPasswordClick -> onForgetPasswordClick()
            LoginEvent.LoginClick -> onLoginClick()
            is LoginEvent.OnEmailTyped -> onEmailTyped(action.email)
            is LoginEvent.OnNameTyped -> onNameTyped(action.name)
            is LoginEvent.OnPasswordTyped -> onPasswordTyped(action.password)
            is LoginEvent.ChangeOperationType -> changeOperationType(action.currentOperationType)
        }
    }

    private fun changeOperationType(currentOperationType: OperationType) {
        if (currentOperationType == state.operationType) return
        state = if (state.operationType is OperationType.Login) {
            state.copy(
                operationType = OperationType.Register,
                loginButtonLabel = "Cadastrar",
                enableButton = validateFields(currentOperationType)
            )
        } else {
            state.copy(
                operationType = OperationType.Login,
                loginButtonLabel = "Entrar",
                enableButton = validateFields(currentOperationType)
            )
        }
    }

    private fun onForgetPasswordClick() {
        TODO("Not yet implemented")
    }

    private fun onLoginClick() {
        viewModelScope.launch {
            when (state.operationType) {
                OperationType.Login -> login()
                OperationType.Register -> register()
            }
        }

    }

    private suspend fun register() {
        //todo
    }

    private suspend fun login() {
        if (state.email != null && state.password != null) {
            loginUseCase.execute(state.email!!, state.password!!)
                .fold(
                    onSuccess = ::handleLoginSuccess,
                    onFailure = ::handleLoginError
                )
        }
    }

    private fun handleLoginSuccess(loginEntity: LoginEntity) {
        println(loginEntity)
    }

    private fun handleLoginError(exception: Throwable) {
        println(exception.message)
    }

    private fun onEmailTyped(email: String) {
        state = state.copy(
            email = email,
            enableButton = validateFields(),
            showEmailError = !isValidEmail(email)
        )
    }

    private fun onNameTyped(name: String) {
        state = state.copy(
            name = name,
            enableButton = validateFields(),
            showNameError = !isValidName(name)
        )
    }

    private fun onPasswordTyped(password: String) {
        state = state.copy(
            password = password,
            enableButton = validateFields(),
            showPasswordError = !isValidPassword(password)
        )
    }

    private fun validateFields(currentOperationType: OperationType? = null): Boolean {
        val operation = currentOperationType ?: state.operationType
        return if (operation == OperationType.Login) {
            isValidEmail(state.email) && isValidPassword(
                state.password
            )
        } else {
            isValidName(state.name) && isValidEmail(state.email) && isValidPassword(state.password)
        }
    }


    private fun isValidEmail(email: String?): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
        return email?.matches(emailRegex.toRegex()) ?: false
    }

    private fun isValidName(name: String?): Boolean {
        val nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(\\s+[A-Za-zÀ-ÖØ-öø-ÿ]+)+$"
        return name
            ?.trim()
            ?.matches(nameRegex.toRegex())
            ?: false
    }


    private fun isValidPassword(password: String?): Boolean {
        val passwordRegex =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$"
        return password?.matches(passwordRegex.toRegex()) ?: false
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
        createAccountOptionButtonLabel = "Criar conta",
        loginOptionButtonLabel = "Entrar",
        passwordErrorText = "Insira uma senha com 1 Letra maiuscula, 1 minuscula e 1 numero com 8 caracteres",
        nameErrorText = "Insira um nome válido",
        emailErrorText = "Insira um email válido"
    )

}