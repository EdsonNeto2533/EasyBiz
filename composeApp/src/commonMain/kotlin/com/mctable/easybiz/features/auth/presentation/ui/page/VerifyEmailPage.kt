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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.text.input.KeyboardType
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
import com.mctable.easybiz.features.auth.presentation.event.VerifyEmailEvent
import com.mctable.easybiz.features.auth.presentation.state.VerifyEmailState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun VerifyEmailPage(
    email: String,
    name: String,
    password: String,
    state: VerifyEmailState,
    onAction: (VerifyEmailEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    //todo deprecado
    BackHandler {
        onAction.invoke(VerifyEmailEvent.OnBackClick)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism("Confirmação de email", showBackArrow = true, onBackClick = {
                onAction.invoke(VerifyEmailEvent.OnBackClick)
            })
        },
        bottomBar = {
            Column {
                ButtonAtom(
                    state.buttonText,
                    isEnabled = state.enableButton,
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            horizontal = Dimens.screenPaddingHorizontal,
                        )
                ) {
                    onAction.invoke(VerifyEmailEvent.ConfirmCode(email, name, password))
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
                state.title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            Text(
                state.subTitle,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            TextInputAtom(
                label = state.inputLabel,
                placeHolder = state.inputPlaceholder,
                showError = state.showEmailError,
                errorMessage = state.emailErrorText,
                keyboardType = KeyboardType.Number,
                onChanged = { code ->
                    onAction.invoke(
                        VerifyEmailEvent.OnCodeTyped(code)
                    )
                }
            )
        }

        if (state.showLoadingDialog) {
            LoadingDialogMolecule()
        }

        AnimatedVisibility(state.showErrorDialog) {
            ErrorDialogMolecule {
                onAction.invoke(VerifyEmailEvent.HideErrorDialog)
            }
        }

    }
}

@Preview
@Composable
fun VerifyEmailPagePreview() {
    EasyBizTheme {
        VerifyEmailPage(
            "",
            "",
            "",
            VerifyEmailState(
                title = "Informe seu código para confirmação",
                subTitle = "Precisamos que informe o código recebido em seu email",
                inputLabel = "Código",
                inputPlaceholder = "0000000",
                emailErrorText = "Insira um código válido",
                buttonText = "Confirmar"
            )
        ) {}
    }
}