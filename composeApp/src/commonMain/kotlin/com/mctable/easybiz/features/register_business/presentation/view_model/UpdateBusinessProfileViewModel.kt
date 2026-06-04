package com.mctable.easybiz.features.register_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.business_details.domain.usecase.GetBusinessDetailsUseCase
import com.mctable.easybiz.features.register_business.domain.usecase.UpdateBusinessProfileUseCase
import com.mctable.easybiz.features.register_business.domain.usecase.AddLogoUseCase
import com.mctable.easybiz.features.register_business.presentation.event.UpdateBusinessProfileEvent
import com.mctable.easybiz.features.register_business.presentation.state.UpdateBusinessProfileState
import kotlinx.coroutines.launch

class UpdateBusinessProfileViewModel(
    private val updateBusinessProfileUseCase: UpdateBusinessProfileUseCase,
    private val addLogoUseCase: AddLogoUseCase,
    private val navigator: Navigator,
    private val getBusinessDetailsUseCase: GetBusinessDetailsUseCase
) : ViewModel() {

    var state by mutableStateOf(UpdateBusinessProfileState())
        private set

    fun onEvent(event: UpdateBusinessProfileEvent) {
        when (event) {
            is UpdateBusinessProfileEvent.LoadBusiness -> handleLoadBusiness(event.id)
            is UpdateBusinessProfileEvent.UpdateBusiness -> handleUpdateBusiness(event.id)
            is UpdateBusinessProfileEvent.NavigateToMedia -> navigator.navigate(Destination.BusinessMedia(event.id))
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

            is UpdateBusinessProfileEvent.WorkingHoursChanged -> {
                state = state.copy(workingHours = event.hours)
            }

            is UpdateBusinessProfileEvent.ImageChanged -> {
                state = state.copy(image = event.image)
            }

            UpdateBusinessProfileEvent.DismissErrorModal -> {
                state = state.copy(isError = false)
            }
        }
    }

    private fun handleLoadBusiness(id: String) {
        state = state.copy(isLoadingData = true)
        viewModelScope.launch {
            getBusinessDetailsUseCase.execute(id).onSuccess { business ->
                state = state.copy(
                    isLoadingData = false,
                    description = business.description ?: "",
                    cellphone = business.telephone?.filter { it.isDigit() } ?: "",
                    workingHours = business.workingHours ?: "",
                    minimalPrice = business.minimalValue?.let {
                        if (it % 1.0 == 0.0) it.toInt().toString() else it.toString()
                    } ?: "",
                    yearsOfExperience = business.yearsOfExperience?.toString() ?: "",
                    logoUrl = business.logoUrl,
                    enableButton = validateFields()
                )
            }.onFailure {
                state = state.copy(isLoadingData = false)
            }
        }
    }

    private fun validateFields(): Boolean =
        state.description.isNotBlank() && state.cellphone.isNotBlank() && state.minimalPrice.isNotBlank() && state.yearsOfExperience.isNotBlank()

    private fun handleUpdateBusiness(id: String) {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            val updateResult = updateBusinessProfileUseCase.execute(
                id = id,
                description = state.description,
                telephone = state.cellphone,
                minimumPrice = state.minimalPrice.toDoubleOrNull() ?: 0.0,
                yearsOfExperience = state.yearsOfExperience.toIntOrNull() ?: 0,
                workingHours = state.workingHours
            )

            updateResult.onSuccess {
                state.image?.let { imageBytes ->
                    updateUserImage(id, imageBytes)
                } ?: run {
                    state = state.copy(isLoading = false, success = true)
                    navigator.pop()
                }
            }.onFailure {
                state = state.copy(isLoading = false, isError = true, errorMessage = it.message)
            }
        }
    }

    private suspend fun updateUserImage(id: String, imageBytes: ByteArray){
        addLogoUseCase.execute(id, imageBytes).fold(
            onSuccess = {
                state = state.copy(isLoading = false, success = true)
                navigator.pop()
            },
            onFailure = {
                state = state.copy(isLoading = false, isError = true, errorMessage = it.message)
            }
        )
    }
}
