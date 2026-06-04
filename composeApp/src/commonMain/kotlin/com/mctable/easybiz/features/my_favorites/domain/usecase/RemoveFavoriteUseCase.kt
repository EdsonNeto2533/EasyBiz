package com.mctable.easybiz.features.my_favorites.domain.usecase

import com.mctable.easybiz.features.my_favorites.domain.repository.MyFavoriteRepository

interface RemoveFavoriteUseCase {
    suspend fun execute(businessId: String): Result<Unit>
}

class RemoveFavoriteUseCaseImpl(
    private val repository: MyFavoriteRepository
) : RemoveFavoriteUseCase {
    override suspend fun execute(businessId: String): Result<Unit> {
        return repository.removeFavorite(businessId)
    }
}