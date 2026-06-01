package com.mctable.easybiz.features.reviews.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.SuccessDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.Neutral200
import com.mctable.easybiz.core.ds.theme.Neutral400
import com.mctable.easybiz.core.ds.theme.Neutral50
import com.mctable.easybiz.core.ds.theme.StarGold
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.reviews.presentation.event.ReviewEvent
import com.mctable.easybiz.features.reviews.presentation.state.ReviewState
import com.mctable.easybiz.features.reviews.presentation.ui.components.ReviewCard

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

@Composable
private fun ReviewFormCard(
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

@Composable
private fun AlreadyReviewedCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimens.cardElevation)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.cardPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "✅",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(Modifier.height(Dimens.spacingSm))
            Text(
                text = "Você já avaliou este pedido",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(Dimens.spacingXs))
            Text(
                text = "Confira as avaliações abaixo",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}