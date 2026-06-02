package com.mctable.easybiz.features.reviews.di

import com.mctable.easybiz.features.reviews.data.datasource.ReviewDatasource
import com.mctable.easybiz.features.reviews.data.datasource.ReviewDatasourceImpl
import com.mctable.easybiz.features.reviews.data.repository.ReviewRepositoryImpl
import com.mctable.easybiz.features.reviews.domain.repository.ReviewRepository
import com.mctable.easybiz.features.reviews.domain.usecase.GetBusinessReviewsUseCase
import com.mctable.easybiz.features.reviews.domain.usecase.GetBusinessReviewsUseCaseImpl
import com.mctable.easybiz.features.reviews.domain.usecase.SubmitReviewUseCase
import com.mctable.easybiz.features.reviews.domain.usecase.SubmitReviewUseCaseImpl
import com.mctable.easybiz.features.reviews.presentation.view_model.ReviewViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reviewModule = module {
    single<ReviewDatasource> {
        ReviewDatasourceImpl(get(), get())
    }

    single<ReviewRepository> {
        ReviewRepositoryImpl(get())
    }

    factory<GetBusinessReviewsUseCase> { GetBusinessReviewsUseCaseImpl(get()) }
    factory<SubmitReviewUseCase> { SubmitReviewUseCaseImpl(get()) }

    viewModel { ReviewViewModel(get(), get(), get()) }
}