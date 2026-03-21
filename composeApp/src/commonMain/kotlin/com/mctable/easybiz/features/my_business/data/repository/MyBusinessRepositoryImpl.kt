package com.mctable.easybiz.features.my_business.data.repository

import com.mctable.easybiz.features.my_business.data.datasource.MyBusinessDatasource
import com.mctable.easybiz.features.my_business.data.mapper.MyBusinessMapper
import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity
import com.mctable.easybiz.features.my_business.domain.repository.MyBusinessRepository

class MyBusinessRepositoryImpl(
    private val datasource: MyBusinessDatasource
) : MyBusinessRepository {
    override suspend fun getMyBusiness(): Result<List<MyBusinessEntity>> = runCatching {
        return datasource.getMyBusiness().map { list ->
            list.map { MyBusinessMapper.toEntity(it) }
        }
    }
}
