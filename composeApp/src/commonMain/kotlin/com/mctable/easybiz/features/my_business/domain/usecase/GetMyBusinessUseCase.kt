package com.mctable.easybiz.features.my_business.domain.usecase

import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity
import com.mctable.easybiz.features.my_business.domain.repository.MyBusinessRepository

class GetMyBusinessUseCase(
    private val repository: MyBusinessRepository
) {
    suspend fun execute(): Result<List<MyBusinessEntity>> {
        return repository.getMyBusiness()
    }
}
