package com.mctable.easybiz.features.business_details.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.business_details.data.dto.CreateOrderRequest
import com.mctable.easybiz.features.business_details.domain.usecase.CreateOrderUseCase
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCase
import com.mctable.easybiz.features.business_details.presentation.event.BusinessDetailsEvent
import com.mctable.easybiz.features.business_details.presentation.state.BusinessDetailsState
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlin.time.Clock

class BusinessDetailsViewModel(
    private val getBusinessDetailsUseCase: GetBusinessDetailsUseCase,
    private val createOrderUseCase: CreateOrderUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(BusinessDetailsState())
        private set

    fun onEvent(event: BusinessDetailsEvent) {
        when (event) {
            is BusinessDetailsEvent.GetBusinessDetails -> handleGetBusinessDetails(event.id)
            BusinessDetailsEvent.CreateOrder -> handleCreateOrder()
            BusinessDetailsEvent.OnBackClick -> handleBackClick()
        }
    }

    private fun handleBackClick() {
        navigator.pop()
    }

    private fun handleGetBusinessDetails(id: String) {
        state = state.copy(showLoading = true, showError = false)
        viewModelScope.launch {
            getBusinessDetailsUseCase.execute(id).fold(
                onSuccess = { details ->
                    state = state.copy(
                        pageTitle = "Perfil do Prestador",
                        availableLabel = "Disponível",
                        addressTitle = "Endereço",
                        startChatLabel = "Iniciar chat",
                        descriptionLabel = "Sobre",
                        businessDetails = details,
                        showLoading = false
                    )
                },
                onFailure = {
                    state = state.copy(
                        showLoading = false,
                        showError = true
                    )
                }
            )
        }
    }

    private fun handleCreateOrder() {
        val business = state.businessDetails ?: return
        state = state.copy(showLoading = true)

        val nextDay = Clock.System.now()
            .plus(1, DateTimeUnit.DAY, TimeZone.UTC)
            .toString()

        val request = CreateOrderRequest(
            businessId = business.id,
            description = business.description ?: business.name,
            desiredDate = nextDay
        )

        viewModelScope.launch {
            createOrderUseCase.execute(request).fold(
                onSuccess = {
                    state = state.copy(showLoading = false)
                    // TODO: Navigate to chat
                },
                onFailure = {
                    state = state.copy(showLoading = false, showError = true)
                }
            )
        }
    }
}
