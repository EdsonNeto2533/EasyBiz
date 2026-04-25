package com.mctable.easybiz.features.my_orders.presentation.event

import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus

sealed class MyOrderEvent {
    data class GetMyOrders(
        val paper: String?,
        val businessId: String?
    ) : MyOrderEvent()

    data object OnBackPressed : MyOrderEvent()
    data class LoadNextPage(
        val paper: String?,
        val businessId: String?
    ) : MyOrderEvent()

    data class OnOrderClick(val orderId: String) : MyOrderEvent()
    data class OnUpdateStatusClick(val orderId: String, val currentStatus: OrderStatus) : MyOrderEvent()
    data object OnDismissBottomSheet : MyOrderEvent()
    data class OnStatusSelected(val orderId: String, val newStatus: OrderStatus) : MyOrderEvent()
}
