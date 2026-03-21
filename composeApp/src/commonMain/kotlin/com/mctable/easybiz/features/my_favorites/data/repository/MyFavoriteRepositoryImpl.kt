package com.mctable.easybiz.features.my_favorites.data.repository

import com.mctable.easybiz.features.my_favorites.data.datasource.MyFavoriteDatasource
import com.mctable.easybiz.features.my_favorites.data.mapper.MyFavoriteMapper
import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity
import com.mctable.easybiz.features.my_favorites.domain.repository.MyFavoriteRepository

class MyFavoriteRepositoryImpl(
    private val datasource: MyFavoriteDatasource
) : MyFavoriteRepository {
    override suspend fun getMyFavorites(): Result<List<MyFavoriteEntity>>  = runCatching {
        return datasource.getMyFavorites().map { list ->
            list.map { MyFavoriteMapper.toEntity(it) }
        }
    }
}
