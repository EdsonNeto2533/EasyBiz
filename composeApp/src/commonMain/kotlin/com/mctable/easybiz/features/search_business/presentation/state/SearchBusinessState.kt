package com.mctable.easybiz.features.search_business.presentation.state

import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity

data class SearchBusinessState(
    val title: String = "",
    val inputPlaceholder: String = "",
    val businessList: List<BusinessEntity> = emptyList(),
    val searchValue: String? = null,
    val showLoading: Boolean = false,
    val showError: Boolean = false,
    val errorMessage: String? = null,
    val currentPage: Int = 0,
    val totalPages: Int = 1,
    val isPaginationLoading: Boolean = false
)
