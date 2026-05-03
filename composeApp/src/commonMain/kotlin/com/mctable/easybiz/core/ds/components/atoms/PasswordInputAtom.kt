package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.theme.Neutral50
import com.mctable.easybiz.core.ds.utils.AppIcons

@Composable
fun PasswordInputAtom(
    label: String,
    placeHolder: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    showError: Boolean = false,
    errorMessage: String? = null
) {

    var text by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation()

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            color = if (showError) MaterialTheme.colorScheme.error
            else MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(Dimens.spacingSm))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onChanged(it)
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
            trailingIcon = {
                IconButton(
                    onClick = { isVisible = !isVisible }
                ) {
                    Icon(
                        painter = if (isVisible)
                            AppIcons.visibilityOn()
                        else
                            AppIcons.visibilityOff(),
                        contentDescription = if (isVisible) "Hide password" else "Show password",
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
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = visualTransformation,
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
