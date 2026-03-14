package com.mctable.easybiz.features.business_details.presentation.state

import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity

data class BusinessDetailsState(
    val businessDetails: BusinessDetailsEntity? = null,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val pageTitle: String = "",
    val availableLabel: String = "",
    val ratingReviewsLabel: String = "",
    val addressTitle: String = "",
    val startChatLabel: String = "",
    val descriptionLabel: String = ""
)
