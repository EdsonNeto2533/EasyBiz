package com.mctable.easybiz.features.reviews.presentation.state

import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity

data class ReviewState(
    val reviews: List<ReviewEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isSubmitting: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null,
    val selectedRating: Int = 0,
    val comment: String = "",
    val submitSuccess: Boolean = false,
    val alreadyReviewed: Boolean = false
)