package com.mctable.easybiz.core.ds.components.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonType
import com.mctable.easybiz.core.ds.theme.Dimens

@Composable
fun EmptyStateMolecule(
    icon: Painter? = null,
    title: String,
    description: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Dimens.spacing3xl),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Icon(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(Dimens.spacing5xl),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )
            Spacer(Modifier.height(Dimens.spacingLg))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

        description?.let {
            Spacer(Modifier.height(Dimens.spacingSm))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(Dimens.spacingXxl))
            ButtonAtom(
                text = actionLabel,
                buttonType = ButtonType.Secondary,
                onClick = onAction
            )
        }
    }
}
