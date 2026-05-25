package com.mctable.easybiz.features.register_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.register_business.presentation.event.UpdateBusinessProfileEvent
import com.mctable.easybiz.features.register_business.presentation.state.UpdateBusinessProfileState
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
fun UpdateBusinessProfilePage(
    id: String,
    state: UpdateBusinessProfileState,
    onEvent: (UpdateBusinessProfileEvent) -> Unit
) {

    val scrollState = rememberScrollState()

    val scope = rememberCoroutineScope()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(UpdateBusinessProfileEvent.ImageChanged(it))
            }
        }
    )

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBarOrganism(
                title = "Atualizar perfil",
                showBackArrow = true,
                onBackClick = { onEvent(UpdateBusinessProfileEvent.OnBackPressed) }
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
                    text = "Salvar alterações",
                    isEnabled = state.enableButton,
                    onClick = { onEvent(UpdateBusinessProfileEvent.UpdateBusiness(id)) }
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
                text = "Informações do prestador",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(Dimens.spacingXxl))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .clickable {
                        singleImagePicker.launch()
                    },
                contentAlignment = Alignment.Center
            ) {

                if (state.image != null) {
                    AsyncImage(
                        model = state.image,
                        contentDescription = "Business Logo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            painter = AppIcons.accountCircle(),
                            contentDescription = null,
                            modifier = Modifier.size(Dimens.iconSizeLg),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(Dimens.spacingSm))
                        Text(
                            text = "Toque para adicionar imagem",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(Modifier.height(Dimens.spacingXxl))

            TextInputAtom(
                label = "Descrição",
                placeHolder = "Descreva seu serviço",
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.DescriptionChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                label = "Telefone",
                placeHolder = "(00) 00000-0000",
                keyboardType = KeyboardType.Phone,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.CellphoneChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                label = "Menor valor cobrado",
                placeHolder = "Ex: 150",
                keyboardType = KeyboardType.Number,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.MinimalPriceChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                label = "Anos de experiência",
                placeHolder = "Ex: 10",
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
                onChanged = {
                    onEvent(UpdateBusinessProfileEvent.YearsOfExperienceChanged(it))
                }
            )

            Spacer(Modifier.height(Dimens.spacing4xl))
        }
    }

    if (state.isLoading) {
        LoadingDialogMolecule()
    }

    AnimatedVisibility(state.isError) {
        ErrorDialogMolecule(
            description = state.errorMessage ?: "Erro desconhecido"
        ) {
            onEvent.invoke(UpdateBusinessProfileEvent.DismissErrorModal)
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
            id = "1"
        )
    }
}
