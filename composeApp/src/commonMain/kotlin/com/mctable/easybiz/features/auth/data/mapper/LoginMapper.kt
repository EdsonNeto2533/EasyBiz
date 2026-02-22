package com.mctable.easybiz.features.auth.data.mapper

import com.mctable.easybiz.features.auth.data.model.LoginResponseModel
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity
import kotlinx.serialization.json.Json

object LoginMapper {
    fun toDomain(response: LoginResponseModel): LoginEntity {
        return LoginEntity(
            email = response.email ?: "",
        )
    }

    fun parseResponse(jsonString: String): LoginResponseModel {
        return Json.decodeFromString<LoginResponseModel>(jsonString)
    }
}
