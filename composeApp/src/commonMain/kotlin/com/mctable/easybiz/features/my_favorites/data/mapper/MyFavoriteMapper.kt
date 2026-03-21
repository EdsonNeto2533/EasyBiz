package com.mctable.easybiz.features.my_favorites.data.mapper

import com.mctable.easybiz.features.my_favorites.data.model.MyFavoriteResponseModel
import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity
import kotlinx.serialization.json.Json

object MyFavoriteMapper {
    private val json = Json { ignoreUnknownKeys = true }

    fun parseListResponse(jsonString: String): List<MyFavoriteResponseModel> {
        return json.decodeFromString(jsonString)
    }

    fun toEntity(model: MyFavoriteResponseModel): MyFavoriteEntity {
        return MyFavoriteEntity(
            id = model.id,
            businessId = model.businessId,
            businessName = model.businessName,
            category = model.category,
            averageRating = model.averageRating,
            logoUrl = model.logoUrl,
            createdAt = model.createdAt
        )
    }
}
