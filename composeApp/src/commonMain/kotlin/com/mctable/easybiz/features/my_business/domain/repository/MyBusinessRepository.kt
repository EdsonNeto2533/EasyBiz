package com.mctable.easybiz.features.my_business.domain.repository

import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity

interface MyBusinessRepository {
    suspend fun getMyBusiness(): Result<List<MyBusinessEntity>>
}
