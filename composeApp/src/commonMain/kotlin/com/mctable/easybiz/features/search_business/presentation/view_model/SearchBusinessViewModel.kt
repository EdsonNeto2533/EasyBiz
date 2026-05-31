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
import com.mctable.easybiz.features.search_business.domain.entity.SearchBusinessPagedEntity
import com.mctable.easybiz.features.search_business.domain.usecase.AddFavoriteUseCase
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCase
import com.mctable.easybiz.features.search_business.presentation.event.SearchBusinessEvent
import com.mctable.easybiz.features.search_business.presentation.state.SearchBusinessState
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchBusinessViewModel(
    private val searchBusinessUseCase: SearchBusinessUseCase,
    private val addFavoriteUseCase: AddFavoriteUseCase,
    private val navigator: Navigator,
    private val locationProvider: LocationProvider
) : ViewModel() {

    var state by mutableStateOf(SearchBusinessState())
        private set

    private val pageSize = 10

    fun onEvent(event: SearchBusinessEvent) {
        when (event) {
            is SearchBusinessEvent.OnSearchValueChange -> handleOnSearchValueChange(event.value)
            SearchBusinessEvent.SearchBusiness -> handleSearchBusinessEvent()
            SearchBusinessEvent.SearchBusinessByName -> handleSearchBusinessByNameEvent()
            is SearchBusinessEvent.SetPermissionController -> handleSetPermissionController(
                event.tracker
            )
            is SearchBusinessEvent.OnBusinessClick -> handleOnBusinessClick(event.id)
            is SearchBusinessEvent.OnFavoriteClick -> handleOnFavoriteClick(event.businessId)
            SearchBusinessEvent.LoadNextPage -> handleLoadNextPage()
        }
    }

    private fun handleOnFavoriteClick(businessId: String) {
        viewModelScope.launch {
            addFavoriteUseCase.execute(businessId)
                .onSuccess {
                    // Success handling if needed
                }
                .onFailure {
                    // Error handling if needed
                }
        }
    }

    private fun handleOnBusinessClick(id: String) {
        navigator.navigate(Destination.BusinessDetails(id))
    }


    private fun handleSetPermissionController(tracker: LocationTracker) {
        locationProvider.setTracker(tracker)
    }

    private fun handleSearchBusinessEvent() {
        state = state.copy(showLoading = true, businessList = emptyList(), currentPage = 0)
        fetchBusinesses()
    }

    private fun handleLoadNextPage() {
        if (state.isPaginationLoading || state.currentPage + 1 >= state.totalPages) return
        state = state.copy(isPaginationLoading = true)
        fetchBusinesses(isNextPage = true)
    }

    private fun fetchBusinesses(isNextPage: Boolean = false) {
        viewModelScope.launch {
            try {
                val location = getLocation()
                val pageToFetch = if (isNextPage) state.currentPage + 1 else 0
                val result = searchBusinessUseCase.execute(
                    location?.latitude ?: 0.0,
                    location?.longitude ?: 0.0,
                    state.searchValue,
                    pageToFetch,
                    pageSize
                )
                result.fold(
                    onSuccess = { pagedEntity -> handleBusinessList(pagedEntity, isNextPage) },
                    onFailure = ::handleBusinessListError
                )
            } catch (e: Exception) {
                handleBusinessListError(e)
            }
        }
    }

    private fun handleBusinessList(pagedEntity: SearchBusinessPagedEntity, isNextPage: Boolean) {
        val newList = if (isNextPage) {
            state.businessList + pagedEntity.content
        } else {
            pagedEntity.content
        }
        state = state.copy(
            businessList = newList,
            showLoading = false,
            isPaginationLoading = false,
            currentPage = pagedEntity.number,
            totalPages = pagedEntity.totalPages
        )
    }

    private fun handleBusinessListError(e: Throwable) {
        state = state.copy(
            showError = true,
            showLoading = false,
            isPaginationLoading = false,
            errorMessage = e.message
        )
    }

    private fun handleSearchBusinessByNameEvent() {
        state = state.copy(showLoading = true, businessList = emptyList(), currentPage = 0)
        fetchBusinesses()
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
