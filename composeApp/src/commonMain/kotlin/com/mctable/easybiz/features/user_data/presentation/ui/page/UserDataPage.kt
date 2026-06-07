package com.mctable.easybiz.features.user_data.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonType
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.ProfilePhotoAction
import com.mctable.easybiz.core.ds.components.molecules.ProfilePhotoViewerDialog
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
    var photoViewerVisible by remember { mutableStateOf(false) }

    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(UserDataEvent.OnImageLoaded(it))
            }
        }
    )

    val viewerImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {
                onEvent(UserDataEvent.UploadPhotoFromViewer(it))
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
                    .clickable { photoViewerVisible = true },
                contentAlignment = Alignment.Center
            ) {
                AvatarAtom(
                    imageUrl = state.user?.photoUrl,
                    contentDescription = "Foto de perfil",
                    size = Dimens.avatarSizeXl
                )
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
                Spacer(modifier = Modifier.height(Dimens.spacingLg))
                ButtonAtom(
                    text = "Excluir Conta",
                    buttonType = ButtonType.Secondary,
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onEvent(UserDataEvent.ShowDeleteConfirmation) }
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

        if (state.showDeleteConfirmation) {
            if (state.deleteConfirmationStep == 1) {
                AlertDialog(
                    onDismissRequest = { onEvent(UserDataEvent.DismissDeleteConfirmation) },
                    title = {
                        Text(
                            text = "Excluir sua conta?",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "Ao excluir sua conta, o seguinte será removido permanentemente:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "• Seus dados pessoais (nome, foto, e-mail)\n• Seu histórico de favoritos\n• Seu perfil de negócio (se houver)\n• Suas avaliações e comentários",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Registros de pedidos e transações podem ser retidos por até 5 anos para cumprimento de obrigações legais (Art. 16, LGPD — Lei 13.709/2018).",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { onEvent(UserDataEvent.AdvanceDeleteStep) }) {
                            Text(
                                text = "Continuar",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { onEvent(UserDataEvent.DismissDeleteConfirmation) }) {
                            Text("Cancelar")
                        }
                    }
                )
            } else {
                AlertDialog(
                    onDismissRequest = { onEvent(UserDataEvent.DismissDeleteConfirmation) },
                    title = {
                        Text(
                            text = "Confirmar exclusão",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "Esta ação é irreversível. Para confirmar, digite EXCLUIR no campo abaixo:",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            OutlinedTextField(
                                value = state.deleteConfirmationInput,
                                onValueChange = { onEvent(UserDataEvent.OnDeleteConfirmationInputChanged(it)) },
                                placeholder = { Text("EXCLUIR") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { onEvent(UserDataEvent.ConfirmDeleteAccount) },
                            enabled = state.deleteConfirmationInput == "EXCLUIR"
                        ) {
                            Text(
                                text = "Excluir permanentemente",
                                color = if (state.deleteConfirmationInput == "EXCLUIR")
                                    MaterialTheme.colorScheme.error
                                else
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { onEvent(UserDataEvent.DismissDeleteConfirmation) }) {
                            Text("Cancelar")
                        }
                    }
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

    if (photoViewerVisible) {
        ProfilePhotoViewerDialog(
            photoUrl = state.user?.photoUrl,
            onDismiss = { photoViewerVisible = false },
            actions = listOf(
                ProfilePhotoAction(
                    icon = {
                        Icon(
                            painter = AppIcons.accountCircle(),
                            contentDescription = null,
                            tint = androidx.compose.ui.graphics.Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = "Editar foto",
                    onClick = {
                        photoViewerVisible = false
                        viewerImagePicker.launch()
                    }
                )
            )
        )
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
