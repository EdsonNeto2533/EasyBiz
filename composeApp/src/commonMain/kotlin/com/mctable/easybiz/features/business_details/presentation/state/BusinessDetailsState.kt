package com.mctable.easybiz.features.business_details.presentation.state

import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity
import com.mctable.easybiz.features.reviews.domain.entity.ReviewEntity

data class BusinessDetailsState(
    val businessDetails: BusinessDetailsEntity? = null,
    val reviews: List<ReviewEntity> = emptyList(),
    val mediaList: List<BusinessMediaEntity> = emptyList(),
    val isOwner: Boolean = false,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val pageTitle: String = "",
    val availableLabel: String = "",
    val ratingReviewsLabel: String = "",
    val addressTitle: String = "",
    val startChatLabel: String = "",
    val descriptionLabel: String = "",
    val errorMessage: String? = null
)
