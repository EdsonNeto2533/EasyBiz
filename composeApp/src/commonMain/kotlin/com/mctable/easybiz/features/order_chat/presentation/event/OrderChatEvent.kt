package com.mctable.easybiz.features.order_chat.presentation.event

sealed class OrderChatEvent {
    data class Init(val orderId: String) : OrderChatEvent()
    data object OnSendMessage : OrderChatEvent()
    data class OnInputTextChanged(val text: String) : OrderChatEvent()
    data object OnBackPressed : OrderChatEvent()
    data object OnLoadMoreMessages : OrderChatEvent()
    data object Disconnect : OrderChatEvent()
}
