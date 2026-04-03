package com.mctable.easybiz.features.my_orders.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.my_orders.domain.usecase.GetMyOrdersUseCase
import com.mctable.easybiz.features.my_orders.presentation.event.MyOrderEvent
import com.mctable.easybiz.features.my_orders.presentation.state.MyOrderState
import kotlinx.coroutines.launch

class MyOrderViewModel(
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MyOrderState())
        private set

    fun onEvent(event: MyOrderEvent) {
        when (event) {
            is MyOrderEvent.GetMyOrders -> loadMyOrders(event.paper, event.businessId)
            MyOrderEvent.OnBackPressed -> navigator.pop()
            is MyOrderEvent.LoadNextPage -> handleNextPage(event.paper, event.businessId)
            is MyOrderEvent.OnOrderClick -> navigator.navigate(Destination.Chat(event.orderId))
        }
    }

    private fun loadMyOrders(
        paper: String? = null,
        businessId: String? = null
    ) {
        state = state.copy(isLoading = true, isError = false, currentPage = 0, orders = emptyList())
        fetchOrders(paper, businessId)
    }

    private fun handleNextPage(
        paper: String? = null,
        businessId: String? = null
    ) {
        if (!state.isLastPage && !state.isLoading) {
            state = state.copy(currentPage = state.currentPage + 1)
            fetchOrders(paper, businessId)
        }
    }

    private fun fetchOrders(
        paper: String?,
        businessId: String?
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getMyOrdersUseCase.execute(
                state.currentPage,
                20,
                paper = paper,
                businessId = businessId
            ).fold(
                onSuccess = { page ->
                    state = state.copy(
                        orders = state.orders + page.content,
                        isLastPage = page.isLast,
                        isLoading = false
                    )
                },
                onFailure = {
                    state = state.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            )
        }
    }
}
