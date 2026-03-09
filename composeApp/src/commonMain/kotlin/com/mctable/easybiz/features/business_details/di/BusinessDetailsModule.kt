package com.mctable.easybiz.features.business_details.di

import com.mctable.easybiz.features.business_details.data.datasource.BusinessDetailsDatasource
import com.mctable.easybiz.features.business_details.data.datasource.BusinessDetailsDatasourceImpl
import com.mctable.easybiz.features.business_details.data.repository.BusinessDetailsRepositoryImpl
import com.mctable.easybiz.features.business_details.domain.repository.BusinessDetailsRepository
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCase
import org.koin.dsl.module

val businessDetailsModule = module {
    single<BusinessDetailsDatasource> {
        BusinessDetailsDatasourceImpl(get(), get())
    }

    single<BusinessDetailsRepository> {
        BusinessDetailsRepositoryImpl(get())
    }

    factory {
        GetBusinessDetailsUseCase(get())
    }
}
