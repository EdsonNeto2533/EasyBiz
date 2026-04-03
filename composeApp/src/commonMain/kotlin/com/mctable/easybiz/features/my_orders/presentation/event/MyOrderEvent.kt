package com.mctable.easybiz.features.my_orders.presentation.event

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
}
