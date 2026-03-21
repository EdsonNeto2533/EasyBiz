package com.mctable.easybiz.features.my_business.di

import com.mctable.easybiz.features.my_business.data.datasource.MyBusinessDatasource
import com.mctable.easybiz.features.my_business.data.datasource.MyBusinessDatasourceImpl
import com.mctable.easybiz.features.my_business.data.repository.MyBusinessRepositoryImpl
import com.mctable.easybiz.features.my_business.domain.repository.MyBusinessRepository
import com.mctable.easybiz.features.my_business.domain.usecase.GetMyBusinessUseCase
import com.mctable.easybiz.features.my_business.presentation.view_model.MyBusinessViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myBusinessModule = module {
    single<MyBusinessDatasource> {
        MyBusinessDatasourceImpl(get(), get())
    }

    single<MyBusinessRepository> {
        MyBusinessRepositoryImpl(get())
    }

    factory { GetMyBusinessUseCase(get()) }

    viewModel { MyBusinessViewModel(get(), get()) }
}
