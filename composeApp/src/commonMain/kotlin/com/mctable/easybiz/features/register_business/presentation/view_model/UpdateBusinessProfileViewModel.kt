package com.mctable.easybiz.features.register_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.register_business.domain.usecase.UpdateBusinessProfileUseCase
import com.mctable.easybiz.features.register_business.domain.usecase.AddLogoUseCase
import com.mctable.easybiz.features.register_business.presentation.event.UpdateBusinessProfileEvent
import com.mctable.easybiz.features.register_business.presentation.state.UpdateBusinessProfileState
import kotlinx.coroutines.launch

class UpdateBusinessProfileViewModel(
    private val businessId: Int,
    private val updateBusinessProfileUseCase: UpdateBusinessProfileUseCase,
    private val addLogoUseCase: AddLogoUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(UpdateBusinessProfileState())
        private set

    fun onEvent(event: UpdateBusinessProfileEvent) {
        when (event) {
            UpdateBusinessProfileEvent.UpdateBusiness -> handleUpdateBusiness()
            UpdateBusinessProfileEvent.OnBackPressed -> navigator.pop()
            is UpdateBusinessProfileEvent.DescriptionChanged -> {
                state = state.copy(description = event.description)
            }
            is UpdateBusinessProfileEvent.CellphoneChanged -> {
                state = state.copy(cellphone = event.cellphone)
            }
            is UpdateBusinessProfileEvent.MinimalPriceChanged -> {
                state = state.copy(minimalPrice = event.price)
            }
            is UpdateBusinessProfileEvent.YearsOfExperienceChanged -> {
                state = state.copy(yearsOfExperience = event.years)
            }
            is UpdateBusinessProfileEvent.ImageChanged -> {
                state = state.copy(image = event.image)
            }
        }
    }

    private fun handleUpdateBusiness() {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            val updateResult = updateBusinessProfileUseCase.execute(
                id = businessId,
                description = state.description,
                telephone = state.cellphone,
                minimumPrice = state.minimalPrice.toDoubleOrNull() ?: 0.0,
                yearsOfExperience = state.yearsOfExperience.toIntOrNull() ?: 0,
                workingHours = "Seg-Sex 8h-18h" // Default or from state
            )

            updateResult.onSuccess {
                state.image?.let { imageBytes ->
                    addLogoUseCase.execute(businessId, imageBytes)
                }
                state = state.copy(isLoading = false, success = true)
                // navigator.pop() or next screen
            }.onFailure {
                state = state.copy(isLoading = false, isError = true)
            }
        }
    }
}
