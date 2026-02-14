package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral300
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
    onClick: () -> Unit
) {
    when (buttonType) {
        ButtonType.Primary -> {
            Button(
                onClick = onClick,
                modifier = Modifier.then(modifier),
                shape = MaterialTheme.shapes.medium,
            ) {
                Text(text = text)
            }
        }

        ButtonType.Secondary -> {
            Button(
                onClick = onClick,
                modifier = Modifier.then(modifier),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Text(text = text)
            }
        }

        ButtonType.Ghost -> {
            Button(
                onClick = onClick,
                modifier = Modifier.then(modifier),
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Neutral300,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text(text = text, color = Neutral500)
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
                "Secondary Action",
                buttonType = ButtonType.Ghost,
                modifier = Modifier.fillMaxWidth()
            ) {}
        }
    }

}