package com.mctable.easybiz.features.auth.di

import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSource
import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSourceImpl
import com.mctable.easybiz.features.auth.data.repository.LoginRepositoryImpl
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCase
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.RegisterUseCase
import com.mctable.easybiz.features.auth.domain.usecase.RegisterUseCaseImpl
import com.mctable.easybiz.features.auth.presentation.view_model.LoginViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    single<LoginRemoteDataSource> {
        LoginRemoteDataSourceImpl(
            networking = get(),
            appEnv = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryImpl(
            remoteDataSource = get(),
            easyBizStorage = get()
        )
    }

    factory<LoginUseCase> {
        LoginUseCaseImpl(
            loginRepository = get()
        )
    }

    factory<RegisterUseCase> {
        RegisterUseCaseImpl(
            loginRepository = get()
        )
    }
}
