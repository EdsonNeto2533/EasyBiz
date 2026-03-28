package com.mctable.easybiz.features.business_details.domain.repository

import com.mctable.easybiz.features.business_details.data.dto.CreateOrderRequest
import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity

interface BusinessDetailsRepository {
    suspend fun getBusinessDetails(id: String): Result<BusinessDetailsEntity>
    suspend fun createOrder(request: CreateOrderRequest): Result<Unit>
}
