package com.mctable.easybiz.features.search_business.domain.usecase

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.features.search_business.domain.entity.SearchBusinessPagedEntity
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

interface SearchBusinessUseCase {
    suspend fun execute(
        latitude: Double,
        longitude: Double,
        name: String?,
        page: Int,
        size: Int
    ): Result<SearchBusinessPagedEntity>
}

class SearchBusinessUseCaseImpl(
    private val searchBusinessRepository: SearchBusinessRepository,
    private val sharedPreferences: EasyBizStorage
) : SearchBusinessUseCase {
    override suspend fun execute(
        latitude: Double,
        longitude: Double,
        name: String?,
        page: Int,
        size: Int
    ): Result<SearchBusinessPagedEntity> {
        val userId = sharedPreferences.getString("userId")
        return searchBusinessRepository.getBusiness(latitude, longitude, name, page, size).map { pagedEntity ->
            pagedEntity.copy(
                content = pagedEntity.content.filter { business -> business.userId != userId }
            )
        }
    }
}
