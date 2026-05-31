package com.mctable.easybiz.features.auth.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.SuccessDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.auth.presentation.event.ResetPasswordEvent
import com.mctable.easybiz.features.auth.presentation.state.ResetPasswordState

@Composable
fun ResetPasswordPage(
    email: String,
    state: ResetPasswordState,
    onAction: (ResetPasswordEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    LaunchedEffect(email) {
        onAction.invoke(ResetPasswordEvent.SetEmail(email))
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism("Redefinir senha", showBackArrow = true, onBackClick = {
                onAction.invoke(ResetPasswordEvent.OnBackClick)
            })
        },
        bottomBar = {
            Column {
                ButtonAtom(
                    "Redefinir Senha",
                    isEnabled = state.isFormValid,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = Dimens.screenPaddingHorizontal)
                ) {
                    onAction.invoke(ResetPasswordEvent.ResetPassword)
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = Dimens.screenPaddingHorizontal)
                .fillMaxHeight()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(Dimens.spacing4xl))
            Text(
                "Crie uma nova senha",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            Text(
                "Insira o código enviado para $email e sua nova senha abaixo.",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            TextInputAtom(
                label = "Código",
                placeHolder = "000000",
                keyboardType = KeyboardType.Number,
                onChanged = { token ->
                    onAction.invoke(ResetPasswordEvent.OnTokenChanged(token))
                }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingMd))

            TextInputAtom(
                label = "Nova Senha",
                placeHolder = "******",
                visualTransformation = PasswordVisualTransformation(),
                onChanged = { password ->
                    onAction.invoke(ResetPasswordEvent.OnNewPasswordChanged(password))
                }
            )

            Spacer(modifier = Modifier.height(Dimens.spacingMd))

            TextInputAtom(
                label = "Confirmar Nova Senha",
                placeHolder = "******",
                visualTransformation = PasswordVisualTransformation(),
                onChanged = { confirmPassword ->
                    onAction.invoke(ResetPasswordEvent.OnConfirmPasswordChanged(confirmPassword))
                }
            )
        }

        if (state.showLoading) {
            LoadingDialogMolecule()
        }

        AnimatedVisibility(state.showErrorDialog) {
            ErrorDialogMolecule(
                description = state.errorMessage ?: "Erro desconhecido",
            ) {
                onAction.invoke(ResetPasswordEvent.DismissError)
            }
        }

        AnimatedVisibility(state.isSuccess) {
            SuccessDialogMolecule(
                title = "Senha redefinida!",
                description = "Sua senha foi alterada com sucesso. Você já pode fazer login."
            ) {
                onAction.invoke(ResetPasswordEvent.OnSuccessDismiss)
            }
        }
    }
}

@Preview
@Composable
fun ResetPasswordPagePreview() {
    EasyBizTheme {
        ResetPasswordPage(
            email = "usuario@email.com",
            state = ResetPasswordState(
                token = "123456",
                isFormValid = true
            ),
            onAction = {}
        )
    }
}
