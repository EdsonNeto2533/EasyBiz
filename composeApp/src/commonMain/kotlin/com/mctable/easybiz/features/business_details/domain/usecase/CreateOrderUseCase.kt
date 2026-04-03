package com.mctable.easybiz.features.business_details.domain.usecase

import com.mctable.easybiz.features.business_details.data.dto.CreateOrderRequest
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository

interface CreateOrderUseCase {
    suspend fun execute(request: CreateOrderRequest): Result<Unit>
}

class CreateOrderUseCaseImpl(
    private val repository: BusinessDetailsRepository
): CreateOrderUseCase {
    override suspend fun execute(request: CreateOrderRequest): Result<Unit> {
        return repository.createOrder(request)
    }
}
