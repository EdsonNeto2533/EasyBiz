package com.mctable.easybiz.features.my_favorites.domain.repository

import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity

interface MyFavoriteRepository {
    suspend fun getMyFavorites(): Result<List<MyFavoriteEntity>>
}
