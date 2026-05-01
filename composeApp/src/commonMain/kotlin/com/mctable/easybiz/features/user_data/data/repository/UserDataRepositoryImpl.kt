package com.mctable.easybiz.features.user_data.data.repository

import com.mctable.easybiz.features.user_data.data.datasource.UserDataDatasource
import com.mctable.easybiz.features.user_data.data.dto.UpdateUserRequest
import com.mctable.easybiz.features.user_data.data.mapper.UserDataMapper
import com.mctable.easybiz.features.user_data.domain.entity.UserEntity
import com.mctable.easybiz.features.user_data.domain.repository.UserDataRepository

class UserDataRepositoryImpl(
    private val datasource: UserDataDatasource
) : UserDataRepository {

    override suspend fun getUserData(): Result<UserEntity> = runCatching {
        return datasource.getUserData().map { UserDataMapper.toDomain(it) }
    }

    override suspend fun updateUserData(name: String): Result<UserEntity> = runCatching {
        return datasource.updateUserData(UpdateUserRequest(fullName = name))
            .map { UserDataMapper.toDomain(it) }
    }

    override suspend fun updateUserPhoto(imageBytes: ByteArray): Result<Unit> = runCatching {
        return datasource.updateUserPhoto(imageBytes)
    }
}
