package com.mctable.easybiz.features.business_details.domain.usecase

import com.mctable.easybiz.features.business_details.data.dto.CreateOrderRequest
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository

class CreateOrderUseCase(
    private val repository: BusinessDetailsRepository
) {
    suspend fun execute(request: CreateOrderRequest): Result<Unit> {
        return repository.createOrder(request)
    }
}
