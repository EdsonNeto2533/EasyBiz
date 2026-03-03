package com.mctable.easybiz.features.search_business.domain.usecase

import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository

interface SearchBusinessUseCase {
    suspend fun execute(
        latitude: Double,
        longitude: Double,
        name: String?
    ): Result<List<BusinessEntity>>
}

class SearchBusinessUseCaseImpl(
    private val searchBusinessRepository: SearchBusinessRepository,
) : SearchBusinessUseCase {
    override suspend fun execute(
        latitude: Double,
        longitude: Double,
        name: String?
    ): Result<List<BusinessEntity>> {
        return searchBusinessRepository.getBusiness(latitude, longitude, name)
    }

}