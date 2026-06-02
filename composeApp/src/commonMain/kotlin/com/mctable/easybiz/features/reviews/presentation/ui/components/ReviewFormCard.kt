package com.mctable.easybiz.features.reviews.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.theme.Neutral50
import com.mctable.easybiz.core.ds.theme.StarGold
import com.mctable.easybiz.core.ds.utils.AppIcons

@Composable
fun ReviewFormCard(
    selectedRating: Int,
    comment: String,
    isSubmitting: Boolean,
    onRatingSelected: (Int) -> Unit,
    onCommentChanged: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation)
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
            Text(
                text = "Avaliar prestador",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(Dimens.spacingMd))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                (1..5).forEach { star ->
                    IconButton(onClick = { onRatingSelected(star) }) {
                        Icon(
                            painter = AppIcons.star(),
                            contentDescription = "Estrela $star",
                            modifier = Modifier.size(Dimens.iconSizeXl),
                            tint = if (star <= selectedRating) StarGold else Neutral400
                        )
                    }
                }
            }

            Spacer(Modifier.height(Dimens.spacingMd))

            OutlinedTextField(
                value = comment,
                onValueChange = onCommentChanged,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Deixe um comentário (opcional)",
                        color = Neutral400,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                shape = MaterialTheme.shapes.medium,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Neutral50,
                    unfocusedContainerColor = Neutral50,
                    unfocusedBorderColor = Neutral200,
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                minLines = 3,
                maxLines = 5
            )

            Spacer(Modifier.height(Dimens.spacingLg))

            ButtonAtom(
                text = "Enviar avaliação",
                isEnabled = selectedRating > 0,
                isLoading = isSubmitting,
                modifier = Modifier.fillMaxWidth(),
                onClick = onSubmit
            )
        }
    }
}

@Preview
@Composable
private fun ReviewFormCardPreview() {
    EasyBizTheme {
        ReviewFormCard(
            selectedRating = 3,
            comment = "Gostei do serviço.",
            isSubmitting = false,
            onRatingSelected = {},
            onCommentChanged = {},
            onSubmit = {}
        )
    }
}
