package com.mctable.easybiz.features.user_data.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonType
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.user_data.domain.entity.UserEntity
import com.mctable.easybiz.features.user_data.presentation.event.UserDataEvent
import com.mctable.easybiz.features.user_data.presentation.state.UserDataState
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserDataPage(
    state: UserDataState,
    onEvent: (UserDataEvent) -> Unit
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(UserDataEvent.OnImageLoaded(it))
            }
        }
    )

    LaunchedEffect(Unit) {
        onEvent(UserDataEvent.LoadUserData)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Meu Perfil",
                showBackArrow = true,
                onBackClick = { onEvent(UserDataEvent.OnBackPressed) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(
                    horizontal = Dimens.screenPaddingHorizontal,
                    vertical = Dimens.screenPaddingVertical
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(Dimens.spacingXxl))

            // Profile Image Section
            Box(
                modifier = Modifier
                    .size(Dimens.avatarSizeXl)
                    .clip(CircleShape)
                    .clickable(enabled = state.isEditMode) {
                        singleImagePicker.launch()
                    },
                contentAlignment = Alignment.Center
            ) {
                AvatarAtom(
                    imageUrl = state.user?.photoUrl,
                    contentDescription = "Foto de perfil",
                    size = Dimens.avatarSizeXl
                )

                if (state.isEditMode) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.Black.copy(alpha = 0.4f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                painter = AppIcons.accountCircle(),
                                contentDescription = "Mudar foto",
                                tint = Color.White,
                                modifier = Modifier.size(Dimens.iconSizeLg)
                            )
                            Text(
                                text = "Alterar",
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacing3xl))

            // User Info Fields
            TextInputAtom(
                initialValue = if (state.isEditMode) state.updatedName else state.user?.name ?: "",
                label = "Nome Completo",
                placeHolder = "Insira seu nome completo",
                onChanged = { onEvent(UserDataEvent.OnNameChanged(it)) },
                enabled = state.isEditMode,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.spacingLg))

            TextInputAtom(
                initialValue = state.user?.email ?: "",
                label = "E-mail",
                placeHolder = "Seu e-mail",
                onChanged = {},
                enabled = false,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimens.spacing4xl))

            // Action Buttons
            if (!state.isEditMode) {
                ButtonAtom(
                    text = "Editar Perfil",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(UserDataEvent.TurnOnEditMode) }
                )
            } else {
                ButtonAtom(
                    text = "Salvar Alterações",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(UserDataEvent.UpdateUserData) }
                )
                Spacer(modifier = Modifier.height(Dimens.spacingMd))
                ButtonAtom(
                    text = "Cancelar",
                    buttonType = ButtonType.Secondary,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(UserDataEvent.TurnOnEditMode) }
                )
            }
        }

        if (state.isLoading) {
            LoadingDialogMolecule()
        }

        AnimatedVisibility(state.error != null) {
            ErrorDialogMolecule(
                description = state.error ?: "Não foi possível carregar os dados.",
                onTryAgain = { onEvent(UserDataEvent.LoadUserData) }
            )
        }
    }
}

@Preview
@Composable
fun UserDataPagePreview() {
    EasyBizTheme {
        UserDataPage(
            state = UserDataState(
                user = UserEntity(
                    id = "1",
                    name = "Marcos Silva",
                    email = "marcos@email.com",
                    photoUrl = "https://cdn.easybiz.com/fotos/marcos.jpg"
                )
            ),
            onEvent = {}
        )
    }
}

@Preview
@Composable
fun UserDataPageEditModePreview() {
    EasyBizTheme {
        UserDataPage(
            state = UserDataState(
                user = UserEntity(
                    id = "1",
                    name = "Marcos Silva",
                    email = "marcos@email.com",
                    photoUrl = null
                ),
                isEditMode = true,
                updatedName = "Marcos Silva Editado"
            ),
            onEvent = {}
        )
    }
}
