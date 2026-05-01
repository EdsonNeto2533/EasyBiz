package com.mctable.easybiz.features.user_data.domain.usecase

import com.mctable.easybiz.features.user_data.domain.repository.UserDataRepository

interface UpdateUserPhotoUseCase {
    suspend fun execute(imageBytes: ByteArray): Result<Unit>
}

class UpdateUserPhotoUseCaseImpl(
    private val repository: UserDataRepository
) : UpdateUserPhotoUseCase {
    override suspend fun execute(imageBytes: ByteArray): Result<Unit> {
        return repository.updateUserPhoto(imageBytes)
    }
}
