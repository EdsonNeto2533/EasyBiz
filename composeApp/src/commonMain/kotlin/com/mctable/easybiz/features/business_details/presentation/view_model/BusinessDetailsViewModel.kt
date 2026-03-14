package com.mctable.easybiz.features.business_details.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCase
import com.mctable.easybiz.features.business_details.presentation.event.BusinessDetailsEvent
import com.mctable.easybiz.features.business_details.presentation.state.BusinessDetailsState
import kotlinx.coroutines.launch

class BusinessDetailsViewModel(
    private val getBusinessDetailsUseCase: GetBusinessDetailsUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(BusinessDetailsState())
        private set

    fun onEvent(event: BusinessDetailsEvent) {
        when (event) {
            is BusinessDetailsEvent.GetBusinessDetails -> handleGetBusinessDetails(event.id)
            BusinessDetailsEvent.StartChat -> handleStartChat()
            BusinessDetailsEvent.OnBackClick -> handleBackClick()
        }
    }

    private fun handleBackClick(){
        navigator.pop()
    }

    private fun handleGetBusinessDetails(id: Int) {
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

    private fun handleStartChat() {
        // TODO: Implement chat navigation or logic
    }
}
