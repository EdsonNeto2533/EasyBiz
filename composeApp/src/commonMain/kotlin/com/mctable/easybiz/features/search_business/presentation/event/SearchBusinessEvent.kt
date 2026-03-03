package com.mctable.easybiz.features.search_business.presentation.event

sealed class SearchBusinessEvent {
    data object SearchBusiness : SearchBusinessEvent()
    data class OnSearchValueChange(val value: String) : SearchBusinessEvent()
    data object SearchBusinessByName : SearchBusinessEvent()
}