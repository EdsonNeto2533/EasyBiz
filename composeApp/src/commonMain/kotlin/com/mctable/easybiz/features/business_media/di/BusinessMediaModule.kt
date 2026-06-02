package com.mctable.easybiz.features.business_media.di

import com.mctable.easybiz.features.business_media.data.datasource.BusinessMediaDatasource
import com.mctable.easybiz.features.business_media.data.datasource.BusinessMediaDatasourceImpl
import com.mctable.easybiz.features.business_media.data.repository.BusinessMediaRepositoryImpl
import com.mctable.easybiz.features.business_media.domain.repository.BusinessMediaRepository
import com.mctable.easybiz.features.business_media.domain.usecase.AddBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.domain.usecase.AddBusinessMediaUseCaseImpl
import com.mctable.easybiz.features.business_media.domain.usecase.DeleteBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.domain.usecase.DeleteBusinessMediaUseCaseImpl
import com.mctable.easybiz.features.business_media.domain.usecase.GetBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.domain.usecase.GetBusinessMediaUseCaseImpl
import com.mctable.easybiz.features.business_media.presentation.view_model.BusinessMediaViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val businessMediaModule = module {
    single<BusinessMediaDatasource> {
        BusinessMediaDatasourceImpl(get(), get(named("multiPart")), get())
    }

    single<BusinessMediaRepository> {
        BusinessMediaRepositoryImpl(get())
    }

    factory<GetBusinessMediaUseCase> { GetBusinessMediaUseCaseImpl(get()) }
    factory<AddBusinessMediaUseCase> { AddBusinessMediaUseCaseImpl(get()) }
    factory<DeleteBusinessMediaUseCase> { DeleteBusinessMediaUseCaseImpl(get()) }

    viewModel { BusinessMediaViewModel(get(), get(), get(), get()) }
}