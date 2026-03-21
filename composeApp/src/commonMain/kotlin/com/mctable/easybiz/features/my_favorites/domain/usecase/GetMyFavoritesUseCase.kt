package com.mctable.easybiz.features.my_favorites.domain.usecase

import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity
import com.mctable.easybiz.features.my_favorites.domain.repository.MyFavoriteRepository

class GetMyFavoritesUseCase(
    private val repository: MyFavoriteRepository
) {
    suspend fun execute(): Result<List<MyFavoriteEntity>> {
        return repository.getMyFavorites()
    }
}
