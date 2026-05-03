package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.theme.Neutral500

sealed class ButtonType {
    object Primary : ButtonType()
    object Secondary : ButtonType()
    object Ghost : ButtonType()
}

@Composable
fun ButtonAtom(
    text: String,
    buttonType: ButtonType = ButtonType.Primary,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
) {
    when (buttonType) {
        ButtonType.Primary -> {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(Dimens.buttonHeight)
                    .then(modifier),
                shape = MaterialTheme.shapes.medium,
                enabled = isEnabled && !isLoading,
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 14.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        ButtonType.Secondary -> {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(Dimens.buttonHeight)
                    .then(modifier),
                shape = MaterialTheme.shapes.medium,
                enabled = isEnabled && !isLoading,
                colors = if (isEnabled) ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ) else ButtonDefaults.buttonColors(
                    containerColor = Neutral200,
                    contentColor = Neutral400
                ),
                border = BorderStroke(
                    1.dp,
                    if (isEnabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                    else Neutral200
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }

        ButtonType.Ghost -> {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(Dimens.buttonHeight)
                    .then(modifier),
                shape = MaterialTheme.shapes.medium,
                enabled = isEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Neutral500
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = Neutral500
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonAtomPreview() {
    EasyBizTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ButtonAtom("Primary Action", modifier = Modifier.fillMaxWidth()) {}
            ButtonAtom(
                "Secondary Action",
                buttonType = ButtonType.Secondary,
                modifier = Modifier.fillMaxWidth()
            ) {}
            ButtonAtom(
                "Ghost Action",
                buttonType = ButtonType.Ghost,
                modifier = Modifier.fillMaxWidth()
            ) {}
        }
    }
}
