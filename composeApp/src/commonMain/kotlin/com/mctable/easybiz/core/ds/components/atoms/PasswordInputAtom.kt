package com.mctable.easybiz.core.ds.components.atoms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.utils.AppIcons

@Composable
fun PasswordInputAtom(
    label: String,
    placeHolder: String,
    onChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var text by remember { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }

    val visualTransformation =
        if (isVisible) VisualTransformation.None
        else PasswordVisualTransformation()

    Column(modifier = modifier) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onChanged(it)
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(placeHolder) },
            trailingIcon = {
                IconButton(
                    onClick = { isVisible = !isVisible }
                ) {
                    Icon(
                        painter = if (isVisible)
                            AppIcons.VisibilityOn()
                        else
                            AppIcons.VisibilityOff(),
                        contentDescription = if (isVisible) "Hide password" else "Show password"
                    )
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                unfocusedBorderColor = Neutral400,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = visualTransformation,
            singleLine = true,
        )
    }
}
