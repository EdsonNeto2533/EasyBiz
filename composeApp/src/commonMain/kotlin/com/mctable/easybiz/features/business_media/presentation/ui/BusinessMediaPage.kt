package com.mctable.easybiz.features.business_media.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity
import com.mctable.easybiz.features.business_media.presentation.event.BusinessMediaEvent
import com.mctable.easybiz.features.business_media.presentation.state.BusinessMediaState
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher

@Composable
fun BusinessMediaPage(
    businessId: String,
    state: BusinessMediaState,
    onEvent: (BusinessMediaEvent) -> Unit
) {
    LaunchedEffect(businessId) {
        onEvent(BusinessMediaEvent.LoadMedia(businessId))
    }

    val scope = rememberCoroutineScope()

    val imagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { bytes ->
                onEvent(BusinessMediaEvent.AddMedia(businessId, bytes, "image/jpeg", "media.jpg"))
            }
        }
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Portfólio (${state.mediaList.size}/20)",
                showBackArrow = true,
                onBackClick = { onEvent(BusinessMediaEvent.OnBackPressed) }
            )
        },
        floatingActionButton = {
            if (state.canAddMore) {
                FloatingActionButton(
                    onClick = { imagePicker.launch() },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = AppIcons.accountCircle(),
                        contentDescription = "Adicionar mídia",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { padding ->

        if (state.mediaList.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = AppIcons.accountCircle(),
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(Dimens.spacingMd))
                    Text(
                        text = "Nenhuma mídia adicionada",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(Dimens.spacingSm))
                    Text(
                        text = "Adicione fotos do seu trabalho",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(Dimens.spacingSm),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSm),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
            ) {
                items(state.mediaList, key = { it.id }) { media ->
                    MediaItem(
                        media = media,
                        onDelete = { onEvent(BusinessMediaEvent.DeleteMedia(businessId, media.id)) }
                    )
                }
            }
        }
    }

    if (state.isLoading || state.isUploading) {
        LoadingDialogMolecule()
    }

    AnimatedVisibility(state.isError) {
        ErrorDialogMolecule(
            description = state.errorMessage ?: "Erro desconhecido"
        ) {
            onEvent(BusinessMediaEvent.DismissError)
        }
    }
}

@Composable
private fun MediaItem(
    media: BusinessMediaEntity,
    onDelete: () -> Unit
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        AsyncImage(
            model = media.url,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        if (media.isVideo) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = AppIcons.accountCircle(),
                    contentDescription = "Vídeo",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(6.dp)
                .size(28.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { onDelete() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = AppIcons.accountCircle(),
                contentDescription = "Remover",
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}