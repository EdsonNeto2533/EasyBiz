package com.mctable.easybiz.features.business_details.presentation.state

import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity

data class BusinessDetailsState(
    val businessDetails: BusinessDetailsEntity? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
