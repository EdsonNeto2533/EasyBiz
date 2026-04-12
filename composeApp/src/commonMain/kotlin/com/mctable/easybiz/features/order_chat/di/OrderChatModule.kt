package com.mctable.easybiz.features.order_chat.di

import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasource
import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasourceImpl
import com.mctable.easybiz.features.order_chat.data.manager.OrderChatWebSocketManager
import com.mctable.easybiz.features.order_chat.data.repository.OrderChatRepositoryImpl
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import com.mctable.easybiz.features.order_chat.domain.usecase.*
import com.mctable.easybiz.features.order_chat.presentation.view_model.OrderChatViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val orderChatModule = module {
    factory<OrderChatDatasource> {
        OrderChatDatasourceImpl(get(), get(), get())
    }
    factory { OrderChatWebSocketManager(get(), get()) }

    single<OrderChatRepository> {
        OrderChatRepositoryImpl(get(), get())
    }

    factory<GetOrderMessagesUseCase> { GetOrderMessagesUseCaseImpl(get()) }
    factory<SendOrderMessageUseCase> { SendOrderMessageUseCaseImpl(get()) }
    factory<ObserveOrderMessagesUseCase> { ObserveOrderMessagesUseCaseImpl(get()) }
    factory<DisconnectChatUseCase> { DisconnectUseCaseImpl(get()) }
    factory<MarkMessageAsReadUseCase> { MarkMessageAsReadUseCaseImpl(get()) }
    factory<SendTypingStatusUseCase> { SendTypingStatusUseCaseImpl(get()) }
    factory<ObserveTypingStatusUseCase> { ObserveTypingStatusUseCaseImpl(get()) }
    factory<ObserveMessageReadStatusUseCase> { ObserveMessageReadStatusUseCaseImpl(get()) }

    viewModel {
        OrderChatViewModel(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
}
