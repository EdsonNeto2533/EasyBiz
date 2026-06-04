package com.mctable.easybiz.features.business_media.presentation.state

import com.mctable.easybiz.features.business_media.domain.entity.BusinessMediaEntity

data class BusinessMediaState(
    val mediaList: List<BusinessMediaEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isUploading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = null
) {
    val canAddMore: Boolean get() = mediaList.size < 20
}