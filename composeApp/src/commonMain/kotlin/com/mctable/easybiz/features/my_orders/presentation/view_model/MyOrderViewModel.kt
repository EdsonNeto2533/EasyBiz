package com.mctable.easybiz.features.my_orders.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderPageEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus
import com.mctable.easybiz.features.my_orders.domain.usecase.GetMyOrdersUseCase
import com.mctable.easybiz.features.my_orders.domain.usecase.UpdateOrderStatusUseCase
import com.mctable.easybiz.features.my_orders.presentation.event.MyOrderEvent
import com.mctable.easybiz.features.my_orders.presentation.state.MyOrderState
import kotlinx.coroutines.launch

class MyOrderViewModel(
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MyOrderState())
        private set

    private val pageSize = 20

    fun onEvent(event: MyOrderEvent) {
        when (event) {
            is MyOrderEvent.GetMyOrders -> {
                state = state.copy(
                    paper = event.paper,
                    businessId = event.businessId,
                    isLoading = true,
                    orders = emptyList(),
                    currentPage = 0
                )
                fetchOrders()
            }
            MyOrderEvent.OnBackPressed -> navigator.pop()
            MyOrderEvent.LoadNextPage -> handleLoadNextPage()
            is MyOrderEvent.OnOrderClick -> navigator.navigate(Destination.Chat(event.orderId))
            is MyOrderEvent.OnUpdateStatusClick -> handleUpdateStatusClick(event.orderId, event.currentStatus)
            MyOrderEvent.OnDismissBottomSheet -> state = state.copy(showStatusBottomSheet = false)
            is MyOrderEvent.OnStatusSelected -> handleStatusSelected(event.orderId, event.newStatus)
        }
    }

    private fun handleLoadNextPage() {
        if (state.isPaginationLoading || state.currentPage + 1 >= state.totalPages) return
        state = state.copy(isPaginationLoading = true)
        fetchOrders(isNextPage = true)
    }

    private fun fetchOrders(isNextPage: Boolean = false) {
        viewModelScope.launch {
            try {
                val pageToFetch = if (isNextPage) state.currentPage + 1 else 0
                getMyOrdersUseCase.execute(
                    pageToFetch,
                    pageSize,
                    paper = state.paper,
                    businessId = state.businessId
                ).fold(
                    onSuccess = { page -> handleOrdersList(page, isNextPage) },
                    onFailure = ::handleOrdersError
                )
            } catch (e: Exception) {
                handleOrdersError(e)
            }
        }
    }

    private fun handleOrdersList(page: MyOrderPageEntity, isNextPage: Boolean) {
        val newList = if (isNextPage) {
            state.orders + page.content
        } else {
            page.content
        }
        state = state.copy(
            orders = newList,
            isLoading = false,
            isPaginationLoading = false,
            currentPage = page.number,
            totalPages = page.totalPages
        )
    }

    private fun handleOrdersError(e: Throwable) {
        state = state.copy(
            isError = true,
            isLoading = false,
            isPaginationLoading = false,
            errorMessage = e.message
        )
    }

    private fun handleUpdateStatusClick(orderId: String, currentStatus: OrderStatus) {
        val options = when (currentStatus) {
            OrderStatus.ABERTO -> listOf(OrderStatus.ACEITO, OrderStatus.RECUSADO)
            OrderStatus.ACEITO -> listOf(OrderStatus.CONCLUIDO, OrderStatus.RECUSADO)
            else -> emptyList()
        }

        if (options.isNotEmpty()) {
            state = state.copy(
                selectedOrderId = orderId,
                availableStatusOptions = options,
                showStatusBottomSheet = true
            )
        }
    }

    private fun handleStatusSelected(orderId: String, newStatus: OrderStatus) {
        state = state.copy(showStatusBottomSheet = false, isLoading = true)
        viewModelScope.launch {
            updateOrderStatusUseCase.execute(orderId, newStatus).fold(
                onSuccess = {
                    onEvent(MyOrderEvent.GetMyOrders(state.paper, state.businessId))
                },
                onFailure = {
                    state = state.copy(isLoading = false, isError = true)
                }
            )
        }
    }
}
