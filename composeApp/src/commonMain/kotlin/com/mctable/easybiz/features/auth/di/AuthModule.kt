package com.mctable.easybiz.features.auth.di

import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSource
import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSourceImpl
import com.mctable.easybiz.features.auth.data.repository.LoginRepositoryImpl
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCase
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCaseImpl
import org.koin.dsl.module

val authModule = module {
    single<LoginRemoteDataSource> {
        LoginRemoteDataSourceImpl(
            networking = get(),
            appEnv = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryImpl(
            remoteDataSource = get()
        )
    }

    single<LoginUseCase> {
        LoginUseCaseImpl(
            loginRepository = get()
        )
    }
}
