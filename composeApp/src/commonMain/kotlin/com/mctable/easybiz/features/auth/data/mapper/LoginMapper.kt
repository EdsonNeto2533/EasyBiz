package com.mctable.easybiz.features.auth.data.mapper

import com.mctable.easybiz.features.auth.data.model.LoginResponseModel
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import kotlinx.serialization.json.Json

object LoginMapper {
    fun toDomain(response: LoginResponseModel): LoginEntity {
        return LoginEntity(
            email = response.email ?: "",
            name = response.name,
            photoUrl = response.photoUrl
        )
    }

    fun parseResponse(jsonString: String): LoginResponseModel {
        val json = Json {
            ignoreUnknownKeys = true
        }
        return json.decodeFromString<LoginResponseModel>(jsonString)
    }
}
