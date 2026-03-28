package com.mctable.easybiz.features.search_business.presentation.event

import dev.icerock.moko.geo.LocationTracker

sealed class SearchBusinessEvent {
    data object SearchBusiness : SearchBusinessEvent()
    data class OnSearchValueChange(val value: String) : SearchBusinessEvent()
    data object SearchBusinessByName : SearchBusinessEvent()
    data class SetPermissionController(val tracker: LocationTracker) : SearchBusinessEvent()
    data class OnBusinessClick(val id: String) : SearchBusinessEvent()
}