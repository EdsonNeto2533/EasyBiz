package com.mctable.easybiz.features.my_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.my_business.domain.usecase.GetMyBusinessUseCase
import com.mctable.easybiz.features.my_business.presentation.event.MyBusinessEvent
import com.mctable.easybiz.features.my_business.presentation.state.MyBusinessState
import kotlinx.coroutines.launch

class MyBusinessViewModel(
    private val getMyBusinessUseCase: GetMyBusinessUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MyBusinessState())
        private set

    fun onEvent(event: MyBusinessEvent) {
        when (event) {
            MyBusinessEvent.GetMyBusiness -> loadMyBusiness()
            MyBusinessEvent.OnBackPressed -> navigator.pop()
            is MyBusinessEvent.OnBusinessClicked -> {
                navigator.navigate(Destination.BusinessDetails(event.id))
            }
            is MyBusinessEvent.OnEditBusinessClicked -> {
                navigator.navigate(Destination.UpdateBusiness(event.id))
            }
        }
    }

    private fun loadMyBusiness() {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            getMyBusinessUseCase.execute().fold(
                onSuccess = { list ->
                    state = state.copy(
                        myBusinessList = list,
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
