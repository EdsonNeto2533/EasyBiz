package com.mctable.easybiz.features.business_details.data.repository

import com.mctable.easybiz.features.business_details.data.datasource.BusinessDetailsDatasource
import com.mctable.easybiz.features.business_details.data.mapper.BusinessDetailsMapper
import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository

class BusinessDetailsRepositoryImpl(
    private val datasource: BusinessDetailsDatasource
) : BusinessDetailsRepository {
    override suspend fun getBusinessDetails(id: Int): Result<BusinessDetailsEntity> = runCatching {
        return datasource.getBusinessDetails(id).map {
            BusinessDetailsMapper.toEntity(it)
        }
    }
}
