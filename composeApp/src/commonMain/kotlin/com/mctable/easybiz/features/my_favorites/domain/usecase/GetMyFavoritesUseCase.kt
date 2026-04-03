package com.mctable.easybiz.features.my_favorites.domain.usecase

import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity
import com.mctable.easybiz.features.my_favorites.domain.repository.MyFavoriteRepository

interface GetMyFavoritesUseCase {
    suspend fun execute(): Result<List<MyFavoriteEntity>>
}

class GetMyFavoritesUseCaseImpl(
    private val repository: MyFavoriteRepository
) : GetMyFavoritesUseCase {
    override suspend fun execute(): Result<List<MyFavoriteEntity>> {
        return repository.getMyFavorites()
    }
}
