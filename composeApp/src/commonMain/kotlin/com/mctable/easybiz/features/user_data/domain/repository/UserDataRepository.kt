package com.mctable.easybiz.features.user_data.domain.repository

import com.mctable.easybiz.features.user_data.domain.entity.UserEntity

interface UserDataRepository {
    suspend fun getUserData(): Result<UserEntity>
    suspend fun updateUserData(name: String): Result<UserEntity>
    suspend fun updateUserPhoto(imageBytes: ByteArray): Result<Unit>
}
