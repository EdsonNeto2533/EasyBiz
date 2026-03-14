package com.mctable.easybiz.features.business_details.presentation.event

sealed class BusinessDetailsEvent {
    data class GetBusinessDetails(val id: Int) : BusinessDetailsEvent()
    data object StartChat : BusinessDetailsEvent()
    data object OnBackClick : BusinessDetailsEvent()
}
