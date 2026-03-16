package com.mctable.easybiz.features.register_business.di

import com.mctable.easybiz.features.register_business.data.datasource.RegisterBusinessDatasource
import com.mctable.easybiz.features.register_business.data.datasource.RegisterBusinessDatasourceImpl
import com.mctable.easybiz.features.register_business.data.repository.RegisterBusinessRepositoryImpl
import com.mctable.easybiz.features.register_business.domain.repository.RegisterBusinessRepository
import com.mctable.easybiz.features.register_business.domain.usecase.CreateBusinessUseCase
import com.mctable.easybiz.features.register_business.domain.usecase.UpdateBusinessProfileUseCase
import com.mctable.easybiz.features.register_business.domain.usecase.AddLogoUseCase
import com.mctable.easybiz.features.register_business.presentation.view_model.RegisterBusinessViewModel
import com.mctable.easybiz.features.register_business.presentation.view_model.UpdateBusinessProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val registerBusinessModule = module {
    single<RegisterBusinessDatasource> {
        RegisterBusinessDatasourceImpl(get(), get())
    }

    single<RegisterBusinessRepository> {
        RegisterBusinessRepositoryImpl(get())
    }

    factory { CreateBusinessUseCase(get()) }
    factory { UpdateBusinessProfileUseCase(get()) }
    factory { AddLogoUseCase(get()) }

    viewModel { RegisterBusinessViewModel(get(), get(), get()) }
    viewModel { UpdateBusinessProfileViewModel( get(), get(), get()) }
}
