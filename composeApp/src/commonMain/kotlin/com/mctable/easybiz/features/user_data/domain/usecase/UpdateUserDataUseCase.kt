package com.mctable.easybiz.features.user_data.domain.usecase

import com.mctable.easybiz.features.user_data.domain.entity.UserEntity
import com.mctable.easybiz.features.user_data.domain.repository.UserDataRepository

interface UpdateUserDataUseCase {
    suspend fun execute(name: String): Result<UserEntity>
}

class UpdateUserDataUseCaseImpl(
    private val repository: UserDataRepository
) : UpdateUserDataUseCase {
    override suspend fun execute(name: String): Result<UserEntity> {
        return repository.updateUserData(name)
    }
}
