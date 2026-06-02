package com.mctable.easybiz.core.ds.components.molecules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.utils.AppIcons

data class ProfilePhotoAction(
    val icon: @Composable () -> Unit,
    val label: String,
    val onClick: () -> Unit
)

@Composable
fun ProfilePhotoViewerDialog(
    photoUrl: String?,
    onDismiss: () -> Unit,
    actions: List<ProfilePhotoAction> = emptyList()
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { onDismiss() }
        ) {
            // Fundo: foto desfocada + overlay escuro
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(30.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.65f))
            )

            // Foto grande no centro
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { /* consome o clique sem fechar */ }
                ) {
                    if (photoUrl != null) {
                        AsyncImage(
                            model = photoUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                                .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(3.dp, Color.White.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = AppIcons.accountCircle(),
                                contentDescription = null,
                                modifier = Modifier.size(100.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Botões de ação na base
            if (actions.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 64.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    actions.forEach { action ->
                        Column(
                            modifier = Modifier
                                .clickable { action.onClick() }
                                .padding(horizontal = 20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .border(1.dp, Color.White.copy(alpha = 0.15f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                action.icon()
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = action.label,
                                color = Color.White,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                        Spacer(Modifier.width(4.dp))
                    }
                }
            }
        }
    }
}