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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.auth.presentation.event.ForgetPasswordEvent
import com.mctable.easybiz.features.auth.presentation.state.ForgetPasswordState

@Composable
fun ForgetPasswordPage(
    state: ForgetPasswordState,
    onAction: (ForgetPasswordEvent) -> Unit
) {
    val scrollState = rememberScrollState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism("Esqueci minha senha", showBackArrow = true, onBackClick = {
                onAction.invoke(ForgetPasswordEvent.OnBackClick)
            })
        },
        bottomBar = {
            Column {
                ButtonAtom(
                    "Enviar e-mail",
                    isEnabled = state.isEmailValid,
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = Dimens.screenPaddingHorizontal)
                ) {
                    onAction.invoke(ForgetPasswordEvent.SendEmail)
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
                "Recuperar senha",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            Text(
                "Informe seu e-mail para receber o código de recuperação.",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            TextInputAtom(
                label = "E-mail",
                placeHolder = "seuemail@exemplo.com",
                onChanged = { email ->
                    onAction.invoke(ForgetPasswordEvent.OnEmailChanged(email))
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
                onAction.invoke(ForgetPasswordEvent.DismissError)
            }
        }
    }
}

@Preview
@Composable
fun ForgetPasswordPagePreview() {
    EasyBizTheme {
        ForgetPasswordPage(
            state = ForgetPasswordState(
                email = "user@example.com",
                isEmailValid = true
            ),
            onAction = {}
        )
    }
}
