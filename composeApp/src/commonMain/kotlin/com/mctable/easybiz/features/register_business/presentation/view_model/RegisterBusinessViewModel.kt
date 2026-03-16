package com.mctable.easybiz.features.register_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.location.LocationProvider
import com.mctable.easybiz.core.location.SimpleLocation
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.register_business.domain.usecase.CreateBusinessUseCase
import com.mctable.easybiz.features.register_business.presentation.event.RegisterBusinessEvent
import com.mctable.easybiz.features.register_business.presentation.state.RegisterBusinessState
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class RegisterBusinessViewModel(
    private val createBusinessUseCase: CreateBusinessUseCase,
    private val navigator: Navigator,
    private val locationProvider: LocationProvider
) : ViewModel() {

    var state by mutableStateOf(RegisterBusinessState())
        private set

    fun onEvent(event: RegisterBusinessEvent) {
        when (event) {
            RegisterBusinessEvent.CreateBusiness -> handleCreateBusiness()
            RegisterBusinessEvent.OnBackPressed -> navigator.pop()
            is RegisterBusinessEvent.BusinessNameChanged -> {
                state = state.copy(businessName = event.name)
            }

            is RegisterBusinessEvent.CategoryNameChanged -> {
                state = state.copy(category = event.category)
            }

            is RegisterBusinessEvent.CompleteAddressChanged -> {
                state = state.copy(completeAddress = event.address)
            }

            is RegisterBusinessEvent.SetPermissionController -> handleSetPermissionController(event.tracker)
        }
    }

    private fun handleSetPermissionController(tracker: LocationTracker) {
        locationProvider.setTracker(tracker)
    }

    private fun handleCreateBusiness() {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            val location = getLocation()
            val result = createBusinessUseCase.execute(
                name = state.businessName,
                category = state.category,
                latitude = location?.latitude ?: 0.0,
                longitude = location?.longitude ?: 0.0,
                completeAddress = state.completeAddress
            )
            result.fold(
                onSuccess = {
                    state = state.copy(isLoading = false, success = true)
                    // Navigate to next step or profile update
                },
                onFailure = {
                    state = state.copy(isLoading = false, isError = true)
                }
            )
        }
    }


    private suspend fun getLocation(): SimpleLocation? {
        locationProvider.start()
        return try {
            locationProvider
                .observeLocation()
                ?.first()
        } finally {
            locationProvider.stop()
        }

    }
}
