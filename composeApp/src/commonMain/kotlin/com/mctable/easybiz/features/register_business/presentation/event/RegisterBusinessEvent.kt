package com.mctable.easybiz.features.register_business.presentation.event

sealed class RegisterBusinessEvent {
    data object CreateBusiness : RegisterBusinessEvent()
    data object OnBackPressed : RegisterBusinessEvent()
    data class BusinessNameChanged(val name: String) : RegisterBusinessEvent()
    data class CategoryNameChanged(val category: String) : RegisterBusinessEvent()
    data class CompleteAddressChanged(val address: String) : RegisterBusinessEvent()
}
