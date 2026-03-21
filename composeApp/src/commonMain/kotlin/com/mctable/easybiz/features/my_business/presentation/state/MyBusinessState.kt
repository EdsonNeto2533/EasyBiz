package com.mctable.easybiz.features.my_business.presentation.state

import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity

data class MyBusinessState(
    val myBusinessList: List<MyBusinessEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
