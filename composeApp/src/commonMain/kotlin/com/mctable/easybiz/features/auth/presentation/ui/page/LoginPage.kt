package com.mctable.easybiz.features.auth.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonType
import com.mctable.easybiz.core.ds.components.atoms.PasswordInputAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral300
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.auth.presentation.event.LoginEvent
import com.mctable.easybiz.features.auth.presentation.state.LoginState
import com.mctable.easybiz.features.auth.presentation.state.OperationType
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import dev.icerock.moko.permissions.location.LOCATION

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun LoginPage(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val permissionControllerFactory = rememberPermissionsControllerFactory()
    val permissionController = remember(permissionControllerFactory) {
        permissionControllerFactory.createPermissionsController()
    }

    BindEffect(permissionController)

    LaunchedEffect(Unit) {
        onEvent.invoke(
            LoginEvent.CheckPermission(
                permissionController.getPermissionState(
                    Permission.LOCATION
                )
            )
        )
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("EasyBiz")
            })
        },
        bottomBar = {
            if (!state.showErrorDialog && !state.showPermissionDeniedForeverScreen && !state.showPermissionDeniedScreen)
                ButtonAtom(
                    state.loginButtonLabel,
                    isEnabled = state.enableButton,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    onEvent.invoke(LoginEvent.LoginClick)
                }
        }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .padding(it)
                    .background(color = Neutral200)
                    .padding(16.dp)
                    .fillMaxHeight().verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Text(state.title, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    state.subTitle,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Neutral300, RoundedCornerShape(16.dp))
                        .padding(horizontal = 6.dp)
                ) {
                    ButtonAtom(
                        state.loginOptionButtonLabel,
                        onClick = {
                            onEvent.invoke(LoginEvent.ChangeOperationType(OperationType.Login))
                        },
                        modifier = Modifier.weight(1f),
                        buttonType = if (state.operationType is OperationType.Login) ButtonType.Secondary else ButtonType.Ghost
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    ButtonAtom(
                        state.createAccountOptionButtonLabel,
                        onClick = {
                            onEvent.invoke(LoginEvent.ChangeOperationType(OperationType.Register))
                        },
                        modifier = Modifier.weight(1f),
                        buttonType = if (state.operationType is OperationType.Register) ButtonType.Secondary else ButtonType.Ghost
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                AnimatedVisibility(visible = state.operationType is OperationType.Register) {
                    Column {
                        TextInputAtom(
                            label = "Nome",
                            placeHolder = "Exemplo nome",
                            showError = state.showNameError,
                            errorMessage = state.nameErrorText,
                            onChanged = { name ->
                                onEvent.invoke(LoginEvent.OnNameTyped(name))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                }
                TextInputAtom(
                    label = state.inputLabel,
                    placeHolder = state.inputPlaceholder,
                    icon = AppIcons.ContactEmail(),
                    showError = state.showEmailError,
                    errorMessage = state.emailErrorText,
                    onChanged = { email ->
                        onEvent.invoke(LoginEvent.OnEmailTyped(email))
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                PasswordInputAtom(
                    label = state.passwordInputLabel,
                    placeHolder = state.passwordInputLabel,
                    showError = state.showPasswordError,
                    errorMessage = state.passwordErrorText,
                    onChanged = { password ->
                        onEvent.invoke(LoginEvent.OnPasswordTyped(password))
                    }
                )
            }

            if (state.showLoadingDialog) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.showErrorDialog) {
                ErrorDialogMolecule {
                    onEvent.invoke(LoginEvent.HideErrorDialog)
                }
            }

            AnimatedVisibility(state.showPermissionDeniedScreen) {
                ErrorDialogMolecule(
                    title = "Precisamos da permissão de localização",
                    description = "Para oferecer a melhor experiência, precisamos acessar sua localização. Ative a permissão nas configurações do aplicativo.",
                    buttonLabel = "Permitir"
                ) {
                    onEvent.invoke(LoginEvent.RequestPermission(permissionController))
                }
            }

            AnimatedVisibility(state.showPermissionDeniedForeverScreen) {
                ErrorDialogMolecule(
                    title = "Permissão de localização desativada",
                    description = "Você escolheu não permitir o acesso à localização. Para utilizar este recurso, é necessário habilitar a permissão manualmente nas configurações do aplicativo.",
                    buttonLabel = "Ok entendi",
                ) {
                    onEvent.invoke(LoginEvent.RequestPermission(permissionController))
                }
            }

        }
    }
}


@Preview
@Composable
fun LoginPagePreview() {
    EasyBizTheme {
        LoginPage(
            LoginState(
                title = "Bem vindo ao EasyBiz",
                subTitle = "Encontre serviços perto de você de forma simples e rápida",
                inputLabel = "E-mail",
                inputPlaceholder = "ex: easybiz@gmail.com",
                passwordInputLabel = "Senha",
                passwordInputPlaceholder = "Sua senha de acesso",
                forgotPasswordLabel = "Esqueci minha senha",
                loginButtonLabel = "Entrar",
                enableButton = true,
                createAccountOptionButtonLabel = "Criar conta",
                loginOptionButtonLabel = "Entrar",
                operationType = OperationType.Register
            )
        ) {}
    }
}