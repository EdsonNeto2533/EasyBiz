package com.mctable.easybiz.features.reviews.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.reviews.domain.usecase.GetBusinessReviewsUseCase
import com.mctable.easybiz.features.reviews.domain.usecase.SubmitReviewUseCase
import com.mctable.easybiz.features.reviews.presentation.event.ReviewEvent
import com.mctable.easybiz.features.reviews.presentation.state.ReviewState
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val getBusinessReviewsUseCase: GetBusinessReviewsUseCase,
    private val submitReviewUseCase: SubmitReviewUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(ReviewState())
        private set

    fun onEvent(event: ReviewEvent) {
        when (event) {
            is ReviewEvent.LoadReviews -> loadReviews(event.businessId)
            ReviewEvent.OnBackPressed -> navigator.pop()
            is ReviewEvent.OnRatingSelected -> state = state.copy(selectedRating = event.rating)
            is ReviewEvent.OnCommentChanged -> state = state.copy(comment = event.comment)
            is ReviewEvent.OnSubmitReview -> submitReview(event.orderId)
            ReviewEvent.OnDismissSuccess -> {
                state = state.copy(submitSuccess = false)
                navigator.pop()
            }
        }
    }

    private var currentOrderId: String = ""

    fun setOrderId(orderId: String) {
        currentOrderId = orderId
    }

    private fun loadReviews(businessId: String) {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            getBusinessReviewsUseCase.execute(businessId).fold(
                onSuccess = { list ->
                    val alreadyReviewed = currentOrderId.isNotBlank() &&
                        list.any { it.orderId == currentOrderId }
                    state = state.copy(reviews = list, isLoading = false, alreadyReviewed = alreadyReviewed)
                },
                onFailure = {
                    state = state.copy(isLoading = false, isError = true, errorMessage = it.message)
                }
            )
        }
    }

    private fun submitReview(orderId: String) {
        if (state.selectedRating == 0) return
        state = state.copy(isSubmitting = true, isError = false)
        viewModelScope.launch {
            submitReviewUseCase.execute(
                orderId = orderId,
                rating = state.selectedRating,
                comment = state.comment.takeIf { it.isNotBlank() }
            ).fold(
                onSuccess = {
                    state = state.copy(isSubmitting = false, submitSuccess = true)
                },
                onFailure = {
                    state = state.copy(isSubmitting = false, isError = true, errorMessage = it.message)
                }
            )
        }
    }
}