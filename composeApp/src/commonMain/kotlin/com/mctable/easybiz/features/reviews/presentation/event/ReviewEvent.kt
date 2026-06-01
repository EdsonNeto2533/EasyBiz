package com.mctable.easybiz.features.reviews.presentation.event

sealed class ReviewEvent {
    data class LoadReviews(val businessId: String) : ReviewEvent()
    data object OnBackPressed : ReviewEvent()
    data class OnRatingSelected(val rating: Int) : ReviewEvent()
    data class OnCommentChanged(val comment: String) : ReviewEvent()
    data class OnSubmitReview(val orderId: String) : ReviewEvent()
    data object OnDismissSuccess : ReviewEvent()
}
