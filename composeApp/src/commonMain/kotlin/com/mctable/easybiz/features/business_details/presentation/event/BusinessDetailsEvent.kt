package com.mctable.easybiz.features.business_details.presentation.event

sealed class BusinessDetailsEvent {
    data class GetBusinessDetails(val id: String) : BusinessDetailsEvent()
    data object CreateOrder : BusinessDetailsEvent()
    data object OnBackClick : BusinessDetailsEvent()
}
