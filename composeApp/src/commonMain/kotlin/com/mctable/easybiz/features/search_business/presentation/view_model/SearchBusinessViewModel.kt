package com.mctable.easybiz.features.search_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.location.LocationProvider
import com.mctable.easybiz.core.location.SimpleLocation
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCase
import com.mctable.easybiz.features.search_business.presentation.event.SearchBusinessEvent
import com.mctable.easybiz.features.search_business.presentation.state.SearchBusinessState
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchBusinessViewModel(
    private val searchBusinessUseCase: SearchBusinessUseCase,
    private val navigator: Navigator,
    private val locationProvider: LocationProvider
) : ViewModel() {

    var state by mutableStateOf(SearchBusinessState())
        private set

    fun onEvent(event: SearchBusinessEvent) {
        when (event) {
            is SearchBusinessEvent.OnSearchValueChange -> handleOnSearchValueChange(event.value)
            SearchBusinessEvent.SearchBusiness -> handleSearchBusinessEvent()
            SearchBusinessEvent.SearchBusinessByName -> handleSearchBusinessByNameEvent()
            is SearchBusinessEvent.SetPermissionController -> handleSetPermissionController(
                event.tracker
            )
            is SearchBusinessEvent.OnBusinessClick -> handleOnBusinessClick(event.id)
        }
    }

    private fun handleOnBusinessClick(id: String) {
        navigator.navigate(Destination.BusinessDetails(id))
    }


    private fun handleSetPermissionController(tracker: LocationTracker) {
        locationProvider.setTracker(tracker)
    }

    private fun handleSearchBusinessEvent() {
        state = state.copy(showLoading = true)
        viewModelScope.launch {
            try {
                val location = getLocation()
                val result =
                    searchBusinessUseCase.execute(
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0,
                        null
                    )
                result.fold(::handleBusinessList, ::handleBusinessListError)
            } catch (e: Exception) {
                handleBusinessListError(e)
                println(e)
            }

        }

    }

    private fun handleBusinessList(businessList: List<BusinessEntity>) {
        state = state.copy(businessList = businessList, showLoading = false)
    }

    private fun handleBusinessListError(e: Throwable) {
        state = state.copy(showError = true, showLoading = false)
    }

    private fun handleSearchBusinessByNameEvent() {
        state = state.copy(showLoading = true)
        viewModelScope.launch {
            val location = getLocation()
            val result = searchBusinessUseCase.execute(
                location?.latitude ?: 0.0,
                location?.longitude ?: 0.0,
                state.searchValue
            )
            result.fold(::handleBusinessList, ::handleBusinessListError)
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

    private fun handleOnSearchValueChange(name: String) {
        state = state.copy(searchValue = name)
    }
}
