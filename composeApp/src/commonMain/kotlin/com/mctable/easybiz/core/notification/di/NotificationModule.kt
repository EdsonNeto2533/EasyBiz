package com.mctable.easybiz.core.notification.di

import com.mctable.easybiz.core.notification.data.datasource.NotificationRemoteDataSource
import com.mctable.easybiz.core.notification.data.datasource.NotificationRemoteDataSourceImpl
import com.mctable.easybiz.core.notification.domain.usecase.RegisterFcmTokenUseCase
import com.mctable.easybiz.core.notification.domain.usecase.RegisterFcmTokenUseCaseImpl
import org.koin.dsl.module

val notificationModule = module {
    single<NotificationRemoteDataSource> {
        NotificationRemoteDataSourceImpl(
            networking = get(),
            appEnv = get()
        )
    }

    factory<RegisterFcmTokenUseCase> {
        RegisterFcmTokenUseCaseImpl(dataSource = get())
    }
}