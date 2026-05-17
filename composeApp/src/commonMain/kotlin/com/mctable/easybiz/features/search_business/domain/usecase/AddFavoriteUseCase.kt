package com.mctable.easybiz.features.search_business.domain.usecase

import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

interface AddFavoriteUseCase {
    suspend fun execute(businessId: String): Result<Unit>
}

class AddFavoriteUseCaseImpl(
    private val repository: SearchBusinessRepository
) : AddFavoriteUseCase {
    override suspend fun execute(businessId: String): Result<Unit> {
        return repository.addFavorite(businessId)
    }
}
