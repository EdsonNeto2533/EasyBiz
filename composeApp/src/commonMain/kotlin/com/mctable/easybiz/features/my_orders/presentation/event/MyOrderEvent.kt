package com.mctable.easybiz.features.my_orders.presentation.event

sealed class MyOrderEvent {
    data object GetMyOrders : MyOrderEvent()
    data object OnBackPressed : MyOrderEvent()
    data object LoadNextPage : MyOrderEvent()
}
