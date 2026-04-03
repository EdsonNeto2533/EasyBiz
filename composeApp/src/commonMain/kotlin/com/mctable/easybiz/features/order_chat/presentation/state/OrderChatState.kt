package com.mctable.easybiz.features.order_chat.presentation.state

import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity

data class OrderChatState(
    val messages: List<OrderChatMessageEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val inputText: String = "",
    val orderId: String = "",
    val isLastPage: Boolean = false,
    val currentPage: Int = 0
)
