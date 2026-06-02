package com.mctable.easybiz.features.reviews.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.SuccessDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity
import com.mctable.easybiz.features.reviews.presentation.event.ReviewEvent
import com.mctable.easybiz.features.reviews.presentation.state.ReviewState
import com.mctable.easybiz.features.reviews.presentation.ui.components.AlreadyReviewedCard
import com.mctable.easybiz.features.reviews.presentation.ui.components.ReviewCard
import com.mctable.easybiz.features.reviews.presentation.ui.components.ReviewFormCard

@Composable
fun ReviewPage(
    state: ReviewState,
    onEvent: (ReviewEvent) -> Unit,
    orderId: String,
    businessId: String
) {
    LaunchedEffect(businessId) {
        onEvent(ReviewEvent.LoadReviews(businessId))
    }

    if (state.submitSuccess) {
        SuccessDialogMolecule(
            title = "Avaliação enviada!",
            description = "Obrigado pelo seu feedback. Ele ajuda outros usuários a encontrar bons prestadores.",
            buttonLabel = "Voltar",
            onDismiss = { onEvent(ReviewEvent.OnDismissSuccess) }
        )
        return
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Avaliações",
                showBackArrow = true,
                onBackClick = { onEvent(ReviewEvent.OnBackPressed) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                contentPadding = PaddingValues(Dimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
            ) {
                item {
                    if (state.alreadyReviewed) {
                        AlreadyReviewedCard()
                    } else {
                        ReviewFormCard(
                            selectedRating = state.selectedRating,
                            comment = state.comment,
                            isSubmitting = state.isSubmitting,
                            onRatingSelected = { onEvent(ReviewEvent.OnRatingSelected(it)) },
                            onCommentChanged = { onEvent(ReviewEvent.OnCommentChanged(it)) },
                            onSubmit = { onEvent(ReviewEvent.OnSubmitReview(orderId)) }
                        )
                    }
                }

                if (state.reviews.isNotEmpty()) {
                    item {
                        Spacer(Modifier.height(Dimens.spacingSm))
                        Text(
                            text = "Avaliações do prestador",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    items(state.reviews.size) { index ->
                        ReviewCard(review = state.reviews[index])
                    }
                } else if (!state.isLoading) {
                    item {
                        Spacer(Modifier.height(Dimens.spacingSm))
                        EmptyStateMolecule(
                            icon = AppIcons.star(),
                            title = "Nenhuma avaliação ainda",
                            description = "Seja o primeiro a avaliar este prestador"
                        )
                    }
                }
            }

            if (state.isLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule(
                    description = state.errorMessage ?: "Erro desconhecido"
                ) {
                    onEvent(ReviewEvent.LoadReviews(businessId))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ReviewPagePreview() {
    EasyBizTheme {
        ReviewPage(
            state = ReviewState(
                reviews = listOf(
                    ReviewEntity(
                        id = "1",
                        reviewerName = "João Silva",
                        rating = 4,
                        comment = "Ótimo atendimento!",
                        evaluatedName = "Limpeza",
                        createdAt = "2024-03-20",
                        orderId = "1"
                    ),
                    ReviewEntity(
                        id = "2",
                        reviewerName = "Maria Souza",
                        rating = 5,
                        comment = "Excelente profissional.",
                        evaluatedName = "Limpeza",
                        createdAt = "2024-03-21",
                        orderId = "2"
                    )
                )
            ),
            onEvent = {},
            orderId = "order_123",
            businessId = "biz_456"
        )
    }
}
