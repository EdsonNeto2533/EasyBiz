package com.mctable.easybiz.features.user_data.di

import com.mctable.easybiz.features.user_data.data.datasource.UserDataDatasource
import com.mctable.easybiz.features.user_data.data.datasource.UserDataDatasourceImpl
import com.mctable.easybiz.features.user_data.data.repository.UserDataRepositoryImpl
import com.mctable.easybiz.features.user_data.domain.repository.UserDataRepository
import com.mctable.easybiz.features.user_data.domain.usecase.GetUserDataUseCase
import com.mctable.easybiz.features.user_data.domain.usecase.GetUserDataUseCaseImpl
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserDataUseCase
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserDataUseCaseImpl
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserPhotoUseCase
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserPhotoUseCaseImpl
import com.mctable.easybiz.features.user_data.presentation.view_model.UserDataViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val userDataModule = module {
    single<UserDataDatasource> {
        UserDataDatasourceImpl(
            networking = get(),
            networkingMultiPart = get(named("multiPart")),
            appEnv = get()
        )
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(get())
    }

    factory<GetUserDataUseCase> {
        GetUserDataUseCaseImpl(get())
    }

    factory<UpdateUserDataUseCase> {
        UpdateUserDataUseCaseImpl(get())
    }

    factory<UpdateUserPhotoUseCase> {
        UpdateUserPhotoUseCaseImpl(get())
    }

    viewModel { UserDataViewModel(get(), get(), get(), get()) }
}
