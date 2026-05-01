package com.mctable.easybiz.features.user_data.data.mapper

import com.mctable.easybiz.features.user_data.data.model.UserDataResponseModel
import com.mctable.easybiz.features.user_data.domain.entity.UserEntity
import kotlinx.serialization.json.Json

object UserDataMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(model: UserDataResponseModel): UserEntity {
        return UserEntity(
            id = model.id,
            name = model.name,
            email = model.email,
            photoUrl = model.photoUrl
        )
    }

    fun parseResponse(jsonString: String): UserDataResponseModel {
        return json.decodeFromString(jsonString)
    }
}
