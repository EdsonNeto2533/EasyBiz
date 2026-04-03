package com.mctable.easybiz.features.order_chat.di

import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasource
import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasourceImpl
import com.mctable.easybiz.features.order_chat.data.repository.OrderChatRepositoryImpl
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import com.mctable.easybiz.features.order_chat.domain.usecase.ConnectToOrderChatUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.ConnectToOrderChatUseCaseImpl
import com.mctable.easybiz.features.order_chat.domain.usecase.GetOrderMessagesUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.GetOrderMessagesUseCaseImpl
import com.mctable.easybiz.features.order_chat.domain.usecase.SendOrderMessageUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.SendOrderMessageUseCaseImpl
import org.koin.dsl.module

val orderChatModule = module {
    single<OrderChatDatasource> {
        OrderChatDatasourceImpl(get(), get())
    }

    single<OrderChatRepository> {
        OrderChatRepositoryImpl(get())
    }

    factory<GetOrderMessagesUseCase> { GetOrderMessagesUseCaseImpl(get()) }
    factory<SendOrderMessageUseCase> { SendOrderMessageUseCaseImpl(get()) }
    factory<ConnectToOrderChatUseCase> { ConnectToOrderChatUseCaseImpl(get()) }
}
