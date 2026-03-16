package com.mctable.easybiz.features.register_business.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.register_business.presentation.event.UpdateBusinessProfileEvent
import com.mctable.easybiz.features.register_business.presentation.state.UpdateBusinessProfileState

@Composable
fun UpdateBusinessProfilePage(
    id: Int,
    state: UpdateBusinessProfileState,
    onEvent: (UpdateBusinessProfileEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    Scaffold(

        topBar = {
            TopAppBarOrganism(
                title = "Atualizar perfil",
                showBackArrow = true,
                onBackClick = { onEvent(UpdateBusinessProfileEvent.OnBackPressed) }
            )
        },

        bottomBar = {

            ButtonAtom(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = "Salvar alterações",
                isEnabled = !state.isLoading,
                onClick = { onEvent(UpdateBusinessProfileEvent.UpdateBusiness(id)) }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Informações do prestador",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        // Placeholder click
                    },
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Adicionar imagem",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            TextInputAtom(
                label = "Descrição",
                placeHolder = "Descreva seu serviço",
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.DescriptionChanged(it))
                }
            )

            Spacer(Modifier.height(16.dp))

            TextInputAtom(
                label = "Telefone",
                placeHolder = "(00) 00000-0000",
                keyboardType = KeyboardType.Phone,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.CellphoneChanged(it))
                }
            )

            Spacer(Modifier.height(16.dp))

            TextInputAtom(
                label = "Menor valor cobrado",
                placeHolder = "Ex: 150",
                keyboardType = KeyboardType.Number,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.MinimalPriceChanged(it))
                }
            )

            Spacer(Modifier.height(16.dp))

            TextInputAtom(
                label = "Anos de experiência",
                placeHolder = "Ex: 10",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.YearsOfExperienceChanged(it))
                }
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}


@Preview
@Composable
fun UpdateBusinessProfilePagePreview() {

    val state = UpdateBusinessProfileState(
        description = "Especialista em manutenção de motores e veículos importados.",
        cellphone = "(11) 99999-9999",
        minimalPrice = "150",
        yearsOfExperience = "10"
    )

    EasyBizTheme {

        UpdateBusinessProfilePage(
            state = state,
            onEvent = {},
            id = 1
        )
    }
}