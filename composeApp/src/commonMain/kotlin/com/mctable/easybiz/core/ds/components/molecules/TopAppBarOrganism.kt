package com.mctable.easybiz.core.ds.components.molecules

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarOrganism(
    title: String,
    showBackArrow: Boolean,
    onBackClick: () -> Unit = {},
    onMenuClick: () -> Unit = {},
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface
        ),
        navigationIcon = {
            if (showBackArrow) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = AppIcons.arrowBack(),
                        contentDescription = "Voltar"
                    )
                }
            } else {
                IconButton(onClick = onMenuClick) {
                    Icon(
                        painter = AppIcons.menu(),
                        contentDescription = "Abrir menu",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun TopAppBarOrganismPreview() {
    EasyBizTheme {
        TopAppBarOrganism(
            title = "EasyBiz",
            showBackArrow = false
        )
    }
}
