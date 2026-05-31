package com.mctable.easybiz.features.auth.di

import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSource
import com.mctable.easybiz.features.auth.data.datasource.LoginRemoteDataSourceImpl
import com.mctable.easybiz.features.auth.data.repository.LoginRepositoryImpl
import com.mctable.easybiz.features.auth.domain.repository.LoginRepository
import com.mctable.easybiz.features.auth.domain.usecase.DeleteAccountUseCase
import com.mctable.easybiz.features.auth.domain.usecase.DeleteAccountUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.ForgetPasswordUseCase
import com.mctable.easybiz.features.auth.domain.usecase.ForgetPasswordUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCase
import com.mctable.easybiz.features.auth.domain.usecase.LoginUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.LogoutUseCase
import com.mctable.easybiz.features.auth.domain.usecase.LogoutUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.RegisterUseCase
import com.mctable.easybiz.features.auth.domain.usecase.RegisterUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.ResetPasswordUseCase
import com.mctable.easybiz.features.auth.domain.usecase.ResetPasswordUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.SendCodeUseCase
import com.mctable.easybiz.features.auth.domain.usecase.SendCodeUseCaseImpl
import com.mctable.easybiz.features.auth.domain.usecase.VerifyEmailUseCase
import com.mctable.easybiz.features.auth.domain.usecase.VerifyEmailUseCaseImpl
import com.mctable.easybiz.features.auth.presentation.view_model.ForgetPasswordViewModel
import com.mctable.easybiz.features.auth.presentation.view_model.LoginViewModel
import com.mctable.easybiz.features.auth.presentation.view_model.ResetPasswordViewModel
import com.mctable.easybiz.features.auth.presentation.view_model.VerifyEmailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authModule = module {
    viewModel { LoginViewModel(get(), get(), get()) }
    viewModelOf(::VerifyEmailViewModel)
    viewModelOf(::ForgetPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)

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

    factory<VerifyEmailUseCase> {
        VerifyEmailUseCaseImpl(
            repository = get()
        )
    }

    factory<SendCodeUseCase> {
        SendCodeUseCaseImpl(
            repository = get()
        )
    }

    factory<LogoutUseCase> {
        LogoutUseCaseImpl(
            repository = get(),
            storage = get()
        )
    }

    factory<DeleteAccountUseCase> {
        DeleteAccountUseCaseImpl(
            repository = get()
        )
    }

    factory<ForgetPasswordUseCase> {
        ForgetPasswordUseCaseImpl(
            repository = get()
        )
    }

    factory<ResetPasswordUseCase> {
        ResetPasswordUseCaseImpl(
            repository = get()
        )
    }
}
