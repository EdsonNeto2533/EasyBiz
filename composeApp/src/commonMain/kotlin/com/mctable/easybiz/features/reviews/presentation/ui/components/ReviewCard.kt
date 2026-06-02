package com.mctable.easybiz.features.reviews.presentation.ui.components

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.components.atoms.RatingAtom
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity

@Composable
fun ReviewCard(review: ReviewEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation)
    ) {
        Column(modifier = Modifier.padding(Dimens.cardPadding)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.reviewerName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    review.evaluatedName?.takeIf { it.isNotBlank() }?.let {
                        Text(
                            text = "Avaliou: $it",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Spacer(Modifier.width(Dimens.spacingSm))
                RatingAtom(rating = review.rating.toDouble())
            }

            review.comment?.takeIf { it.isNotBlank() }?.let { comment ->
                Spacer(Modifier.height(Dimens.spacingXs))
                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun ReviewCardPreview() {
    EasyBizTheme {
        ReviewCard(
            review = ReviewEntity(
                id = "1",
                reviewerName = "João Silva",
                rating = 4,
                comment = "Ótimo atendimento e serviço de qualidade. Recomendo!",
                evaluatedName = "Limpeza Residencial",
                createdAt = "2024-03-20",
                orderId = "order_123"
            )
        )
    }
}
