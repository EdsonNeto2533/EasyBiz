package com.mctable.easybiz.features.my_business.presentation.event

sealed class MyBusinessEvent {
    data object GetMyBusiness : MyBusinessEvent()
    data object OnBackPressed : MyBusinessEvent()
    data class OnBusinessClicked(val id: String) : MyBusinessEvent()
    data class OnEditBusinessClicked(val id: String) : MyBusinessEvent()
}
