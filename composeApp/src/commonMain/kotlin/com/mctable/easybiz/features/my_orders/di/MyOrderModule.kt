package com.mctable.easybiz.features.my_orders.di

import com.mctable.easybiz.features.my_orders.data.datasource.MyOrderDatasource
import com.mctable.easybiz.features.my_orders.data.datasource.MyOrderDatasourceImpl
import com.mctable.easybiz.features.my_orders.data.repository.MyOrderRepositoryImpl
import com.mctable.easybiz.features.my_orders.domain.repository.MyOrderRepository
import com.mctable.easybiz.features.my_orders.domain.usecase.GetMyOrdersUseCase
import com.mctable.easybiz.features.my_orders.presentation.view_model.MyOrderViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val myOrderModule = module {
    single<MyOrderDatasource> {
        MyOrderDatasourceImpl(get(), get())
    }

    single<MyOrderRepository> {
        MyOrderRepositoryImpl(get())
    }

    factory { GetMyOrdersUseCase(get()) }

    viewModel { MyOrderViewModel(get(), get()) }
}
