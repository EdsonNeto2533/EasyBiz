package com.mctable.easybiz.features.auth.presentation.ui.page

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
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.auth.presentation.event.VerifyEmailEvent
import com.mctable.easybiz.features.auth.presentation.state.VerifyEmailState

@Composable
fun VerifyEmailPage(
    state: VerifyEmailState,
    onAction: (VerifyEmailEvent) -> Unit
) {

    val scrollState = rememberScrollState()

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
                    onAction.invoke(VerifyEmailEvent.SendCode)
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
                onChanged = { email ->
                    onAction.invoke(
                        VerifyEmailEvent.OnEmailTyped(email)
                    )
                }
            )
        }

    }
}

@Preview
@Composable
fun VerifyEmailPagePreview() {
    EasyBizTheme {
        VerifyEmailPage(
            VerifyEmailState(
                title = "Informe seu email para confirmação",
                subTitle = "Precisamos que informe seu email para o envio do código de confirmação",
                inputLabel = "E-mail",
                inputPlaceholder = "ex: easybiz@gmail.com",
                emailErrorText = "Insira um email válido",
                buttonText = "enviar código"
            )
        ) {}
    }
}