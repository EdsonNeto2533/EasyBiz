package com.mctable.easybiz.features.my_business.domain.usecase

import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity
import com.mctable.easybiz.features.my_business.domain.repository.MyBusinessRepository

interface GetMyBusinessUseCase {
    suspend fun execute(): Result<List<MyBusinessEntity>>
}

class GetMyBusinessUseCaseImpl(
    private val repository: MyBusinessRepository
): GetMyBusinessUseCase {
    override suspend fun execute(): Result<List<MyBusinessEntity>> {
        return repository.getMyBusiness()
    }
}
