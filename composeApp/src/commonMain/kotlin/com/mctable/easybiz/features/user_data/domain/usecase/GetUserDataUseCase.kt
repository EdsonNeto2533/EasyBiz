package com.mctable.easybiz.features.user_data.domain.usecase

import com.mctable.easybiz.features.user_data.domain.entity.UserEntity
import com.mctable.easybiz.features.user_data.domain.repository.UserDataRepository

interface GetUserDataUseCase {
    suspend fun execute(): Result<UserEntity>
}

class GetUserDataUseCaseImpl(
    private val repository: UserDataRepository
) : GetUserDataUseCase {
    override suspend fun execute(): Result<UserEntity> {
        return repository.getUserData()
    }
}
