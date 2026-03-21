package com.mctable.easybiz.features.my_favorites.presentation.state

import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity

data class MyFavoriteState(
    val favoriteList: List<MyFavoriteEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false
)
