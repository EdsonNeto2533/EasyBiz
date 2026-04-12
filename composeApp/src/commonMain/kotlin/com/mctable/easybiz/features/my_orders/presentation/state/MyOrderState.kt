package com.mctable.easybiz.features.my_orders.presentation.state

import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus

data class MyOrderState(
    val orders: List<MyOrderEntity> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false,
    val selectedOrderId: String? = null,
    val showStatusBottomSheet: Boolean = false,
    val availableStatusOptions: List<OrderStatus> = emptyList()
)
