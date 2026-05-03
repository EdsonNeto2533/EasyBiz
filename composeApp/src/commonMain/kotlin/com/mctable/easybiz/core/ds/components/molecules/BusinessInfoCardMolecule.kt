package com.mctable.easybiz.core.ds.components.molecules

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.theme.Dimens

@Composable
fun BusinessInfoCardMolecule(
    title: String,
    subtitle: String,
    logoUrl: String?,
    onClick: () -> Unit,
    onEditClick: (() -> Unit)? = null,
    onOrdersClick: (() -> Unit)? = null,
    extraContent: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .then(modifier),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.cardElevation
        )
    ) {
        Row(
            modifier = Modifier.padding(Dimens.cardPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AvatarAtom(
                imageUrl = logoUrl,
                contentDescription = title,
                size = Dimens.avatarSizeMd
            )

            Spacer(Modifier.width(Dimens.spacingMd))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(Dimens.spacingXs))

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (onEditClick != null) {
                    TextButton(onClick = onEditClick) {
                        Text(
                            "Editar",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                if (onOrdersClick != null) {
                    TextButton(onClick = onOrdersClick) {
                        Text(
                            "Pedidos",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        extraContent?.invoke()
    }
}
