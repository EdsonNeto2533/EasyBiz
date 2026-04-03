package com.mctable.easybiz.features.search_business.domain.usecase

import com.mctable.easybiz.core.local_storage.EasyBizStorage
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
    private val sharedPreferences: EasyBizStorage
) : SearchBusinessUseCase {
    override suspend fun execute(
        latitude: Double,
        longitude: Double,
        name: String?
    ): Result<List<BusinessEntity>> {
        val userId = sharedPreferences.getString("userId")
        return searchBusinessRepository.getBusiness(latitude, longitude, name).map {
            it.filter { business -> business.userId != userId }
        }
    }

}