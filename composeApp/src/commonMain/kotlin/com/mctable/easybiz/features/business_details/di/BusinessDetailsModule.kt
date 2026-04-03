package com.mctable.easybiz.features.business_details.di

import com.mctable.easybiz.features.business_details.data.datasource.BusinessDetailsDatasource
import com.mctable.easybiz.features.business_details.data.datasource.BusinessDetailsDatasourceImpl
import com.mctable.easybiz.features.business_details.data.repository.BusinessDetailsRepositoryImpl
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository
import com.mctable.easybiz.features.business_details.domain.usecase.CreateOrderUseCase
import com.mctable.easybiz.features.business_details.domain.usecase.CreateOrderUseCaseImpl
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCase
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCaseImpl
import com.mctable.easybiz.features.business_details.presentation.view_model.BusinessDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val businessDetailsModule = module {
    single<BusinessDetailsDatasource> {
        BusinessDetailsDatasourceImpl(get(), get())
    }

    single<BusinessDetailsRepository> {
        BusinessDetailsRepositoryImpl(get())
    }

    factory<GetBusinessDetailsUseCase> {
        GetBusinessDetailsUseCaseImpl(get())
    }

    factory<CreateOrderUseCase> {
        CreateOrderUseCaseImpl(get())
    }

    viewModel { BusinessDetailsViewModel(get(), get(), get()) }
}
