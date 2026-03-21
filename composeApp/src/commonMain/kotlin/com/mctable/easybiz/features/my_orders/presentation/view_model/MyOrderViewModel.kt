package com.mctable.easybiz.features.my_orders.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        onEvent(MyOrderEvent.GetMyOrders)
    }

    fun onEvent(event: MyOrderEvent) {
        when (event) {
            MyOrderEvent.GetMyOrders -> loadMyOrders()
            MyOrderEvent.OnBackPressed -> navigator.pop()
            MyOrderEvent.LoadNextPage -> handleNextPage()
        }
    }

    private fun loadMyOrders() {
        state = state.copy(isLoading = true, isError = false, currentPage = 0, orders = emptyList())
        fetchOrders()
    }

    private fun handleNextPage() {
        if (!state.isLastPage && !state.isLoading) {
            state = state.copy(currentPage = state.currentPage + 1)
            fetchOrders()
        }
    }

    private fun fetchOrders() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getMyOrdersUseCase.execute(state.currentPage, 20).fold(
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
