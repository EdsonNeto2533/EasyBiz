package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.theme.Neutral50
import com.mctable.easybiz.core.ds.utils.MaskVisualTransformation

@Composable
fun TextInputAtom(
    label: String,
    placeHolder: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    initialValue: String = "",
    enabled: Boolean = true,
    icon: Painter? = null,
    leadingIcon: Painter? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    mask: String? = null,
    showError: Boolean = false,
    errorMessage: String? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction = ImeAction.Next,
    visualTransformation: VisualTransformation? = null
) {
    var text by remember(initialValue) { mutableStateOf(initialValue) }
    var isFocused by remember { mutableStateOf(false) }

    val currentVisualTransformation = when {
        visualTransformation != null -> visualTransformation
        mask != null -> MaskVisualTransformation(mask)
        else -> VisualTransformation.None
    }

    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleSmall,
                color = if (showError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(Dimens.spacingSm))
        }
        OutlinedTextField(
            value = text,
            enabled = enabled,
            keyboardActions = keyboardActions,
            onValueChange = {
                val newText = if (mask != null) it.take(mask.count { c -> c == '#' }) else it
                text = newText
                onChanged(newText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused },
            placeholder = {
                Text(
                    placeHolder,
                    color = Neutral400,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            leadingIcon = leadingIcon?.let {
                {
                    Icon(
                        painter = leadingIcon,
                        contentDescription = label,
                        tint = if (isFocused) MaterialTheme.colorScheme.primary else Neutral400
                    )
                }
            },
            trailingIcon = {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = label,
                        tint = if (isFocused) MaterialTheme.colorScheme.primary else Neutral400
                    )
                }
            },
            shape = MaterialTheme.shapes.medium,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Neutral50,
                unfocusedContainerColor = Neutral50,
                disabledContainerColor = Neutral50,
                unfocusedBorderColor = Neutral200,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                errorBorderColor = MaterialTheme.colorScheme.error,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation = currentVisualTransformation,
            singleLine = true,
            isError = showError,
            supportingText = {
                if (showError) {
                    errorMessage?.let {
                        Text(
                            errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun TextInputAtomPreview() {
    EasyBizTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                TextInputAtom(
                    label = "WhatsApp ou E-mail",
                    placeHolder = "Ex: (11) 99999-9999",
                    onChanged = {},
                    keyboardType = KeyboardType.Phone,
                    mask = "(##) #####-####"
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextInputAtom(
                    label = "Your Name",
                    placeHolder = "Ex: John Doe",
                    onChanged = {}
                )
                TextInputAtom(
                    label = "Your Name",
                    placeHolder = "Ex: John Doe",
                    onChanged = {},
                    showError = true,
                    errorMessage = "Nome invalido"
                )
            }
        }
    }
}
