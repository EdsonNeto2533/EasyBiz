package com.mctable.easybiz.features.my_favorites.di

import com.mctable.easybiz.features.my_favorites.data.datasource.MyFavoriteDatasource
import com.mctable.easybiz.features.my_favorites.data.datasource.MyFavoriteDatasourceImpl
import com.mctable.easybiz.features.my_favorites.data.repository.MyFavoriteRepositoryImpl
import com.mctable.easybiz.features.my_favorites.domain.repository.MyFavoriteRepository
import com.mctable.easybiz.features.my_favorites.domain.usecase.GetMyFavoritesUseCase
import com.mctable.easybiz.features.my_favorites.domain.usecase.GetMyFavoritesUseCaseImpl
import com.mctable.easybiz.features.my_favorites.presentation.view_model.MyFavoriteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myFavoriteModule = module {
    single<MyFavoriteDatasource> {
        MyFavoriteDatasourceImpl(get(), get())
    }

    single<MyFavoriteRepository> {
        MyFavoriteRepositoryImpl(get())
    }

    factory<GetMyFavoritesUseCase> { GetMyFavoritesUseCaseImpl(get()) }

    viewModel { MyFavoriteViewModel(get(), get()) }
}
