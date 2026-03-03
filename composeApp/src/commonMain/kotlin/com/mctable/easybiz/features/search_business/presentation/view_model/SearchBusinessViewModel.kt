package com.mctable.easybiz.features.search_business.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.location.LocationProvider
import com.mctable.easybiz.core.location.SimpleLocation
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCase
import com.mctable.easybiz.features.search_business.presentation.event.SearchBusinessEvent
import com.mctable.easybiz.features.search_business.presentation.state.SearchBusinessState
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
        }
    }

    private fun handleSearchBusinessEvent() {
        viewModelScope.launch {
            val location = getLocation()
            val result = searchBusinessUseCase.execute(location.latitude, location.longitude, null)
            result.fold(::handleBusinessList, ::handleBusinessListError)
        }

    }

    private fun handleBusinessList(businessList: List<BusinessEntity>) {
        state = state.copy(businessList = businessList)
    }

    private fun handleBusinessListError(e: Throwable) {
        state = state.copy(showError = true)
    }

    private fun handleSearchBusinessByNameEvent() {
        viewModelScope.launch {
            val location = getLocation()
            val result = searchBusinessUseCase.execute(
                location.latitude,
                location.longitude,
                state.searchValue
            )
            result.fold(::handleBusinessList, ::handleBusinessListError)
        }
    }

    private suspend fun getLocation(): SimpleLocation {
        locationProvider.start()
        val result = locationProvider.observeLocation().first()
        locationProvider.stop()
        return result
    }

    private fun handleOnSearchValueChange(name: String) {
        state = state.copy(searchValue = name)
    }
}