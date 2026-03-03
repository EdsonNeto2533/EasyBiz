package com.mctable.easybiz.features.search_business.domain.repository

import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity

interface SearchBusinessRepository {
    suspend fun getBusiness(): Result<List<BusinessEntity>>
}