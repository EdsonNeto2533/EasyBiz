package com.mctable.easybiz.features.search_business.di

import com.mctable.easybiz.features.search_business.data.datasource.SearchBusinessDatasource
import com.mctable.easybiz.features.search_business.data.datasource.SearchBusinessDatasourceImpl
import com.mctable.easybiz.features.search_business.data.repository.SearchBusinessRepositoryImpl
import com.mctable.easybiz.features.search_business.domain.repository.SearchBusinessRepository
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCase
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCaseImpl
import org.koin.dsl.module

val searchBusinessModule = module {
    single<SearchBusinessDatasource> {
        SearchBusinessDatasourceImpl(get(), get())
    }

    single<SearchBusinessRepository> {
        SearchBusinessRepositoryImpl(get())
    }

    factory<SearchBusinessUseCase> {
        SearchBusinessUseCaseImpl(get())
    }
}