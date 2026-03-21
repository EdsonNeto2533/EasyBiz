package com.mctable.easybiz.features.my_favorites.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.my_favorites.data.mapper.MyFavoriteMapper
import com.mctable.easybiz.features.my_favorites.data.model.MyFavoriteResponseModel

interface MyFavoriteDatasource {
    suspend fun getMyFavorites(): Result<List<MyFavoriteResponseModel>>
}

class MyFavoriteDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
) : MyFavoriteDatasource {
    override suspend fun getMyFavorites(): Result<List<MyFavoriteResponseModel>> {
        return networking.get(
            host = appEnv.host,
            path = "/favoritos",
            responseMapper = { jsonString ->
                MyFavoriteMapper.parseListResponse(jsonString)
            }
        )
    }
}
