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
    private val updateBusinessProfileUseCase: UpdateBusinessProfileUseCase,
    private val addLogoUseCase: AddLogoUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(UpdateBusinessProfileState())
        private set

    fun onEvent(event: UpdateBusinessProfileEvent) {
        when (event) {
            is UpdateBusinessProfileEvent.UpdateBusiness -> handleUpdateBusiness(event.id)
            UpdateBusinessProfileEvent.OnBackPressed -> navigator.pop()
            is UpdateBusinessProfileEvent.DescriptionChanged -> {
                state = state.copy(description = event.description, enableButton = validateFields())
            }

            is UpdateBusinessProfileEvent.CellphoneChanged -> {
                state = state.copy(cellphone = event.cellphone, enableButton = validateFields())
            }

            is UpdateBusinessProfileEvent.MinimalPriceChanged -> {
                state = state.copy(minimalPrice = event.price, enableButton = validateFields())
            }

            is UpdateBusinessProfileEvent.YearsOfExperienceChanged -> {
                state = state.copy(yearsOfExperience = event.years, enableButton = validateFields())
            }

            is UpdateBusinessProfileEvent.ImageChanged -> {
                state = state.copy(image = event.image)
            }

            UpdateBusinessProfileEvent.DismissErrorModal -> {
                state = state.copy(isError = false)
            }
        }
    }

    private fun validateFields(): Boolean =
        state.description.isNotBlank() && state.cellphone.isNotBlank() && state.minimalPrice.isNotBlank() && state.yearsOfExperience.isNotBlank()

    private fun handleUpdateBusiness(id: Int) {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            val updateResult = updateBusinessProfileUseCase.execute(
                id = id,
                description = state.description,
                telephone = state.cellphone,
                minimumPrice = state.minimalPrice.toDoubleOrNull() ?: 0.0,
                yearsOfExperience = state.yearsOfExperience.toIntOrNull() ?: 0,
                workingHours = "Seg-Sex 8h-18h" // Default or from state
            )

            updateResult.onSuccess {
                state.image?.let { imageBytes ->
                    addLogoUseCase.execute(id, imageBytes)
                }
                state = state.copy(isLoading = false, success = true)
                // navigator.pop() or next screen
            }.onFailure {
                state = state.copy(isLoading = false, isError = true)
            }
        }
    }
}
