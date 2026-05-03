package com.mctable.easybiz.features.register_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.helpers.BindLocationTracker
import com.mctable.easybiz.core.helpers.rememberLocationTracker
import com.mctable.easybiz.features.register_business.presentation.event.RegisterBusinessEvent
import com.mctable.easybiz.features.register_business.presentation.state.RegisterBusinessState

@Composable
fun RegisterBusinessPage(
    state: RegisterBusinessState,
    onEvent: (RegisterBusinessEvent) -> Unit
) {

    val tracker = rememberLocationTracker()

    BindLocationTracker(tracker)

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        onEvent.invoke(RegisterBusinessEvent.SetPermissionController(tracker))
    }

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBarOrganism(
                title = "Cadastrar empresa",
                showBackArrow = true,
                onBackClick = { onEvent(RegisterBusinessEvent.OnBackPressed) }
            )
        },

        bottomBar = {
            Column {
                ButtonAtom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.screenPaddingHorizontal,
                        ),
                    text = "Cadastrar empresa",
                    isEnabled = state.enableButton,
                    onClick = { onEvent(RegisterBusinessEvent.CreateBusiness) }
                )
                Box(modifier = Modifier.height(12.dp))
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = Dimens.screenPaddingHorizontal)
                .fillMaxSize()
        ) {

            Spacer(Modifier.height(Dimens.spacingXxl))

            Text(
                text = "Informações da empresa",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(Dimens.spacingXxl))

            TextInputAtom(
                label = "Nome da empresa",
                placeHolder = "Ex: Mecânica do Ricardo",
                onChanged = {
                    onEvent(RegisterBusinessEvent.BusinessNameChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                label = "Categoria",
                placeHolder = "Ex: Mecânico",
                onChanged = {
                    onEvent(RegisterBusinessEvent.CategoryNameChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                label = "Endereço completo",
                placeHolder = "Rua, número, bairro, cidade",
                onChanged = {
                    onEvent(RegisterBusinessEvent.CompleteAddressChanged(it))
                },
                imeAction = ImeAction.Done
            )

            Spacer(Modifier.height(Dimens.spacing4xl))

            if (state.isLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule {
                    onEvent.invoke(RegisterBusinessEvent.DismissErrorModal)
                }
            }
        }
    }
}


@Preview
@Composable
fun RegisterBusinessPagePreview() {

    val state = RegisterBusinessState(
        businessName = "Mecânica do Ricardo",
        category = "Mecânico",
        completeAddress = "Av. Paulista, 1500 - Bela Vista - São Paulo"
    )

    EasyBizTheme {

        RegisterBusinessPage(
            state = state,
            onEvent = {}
        )
    }
}
