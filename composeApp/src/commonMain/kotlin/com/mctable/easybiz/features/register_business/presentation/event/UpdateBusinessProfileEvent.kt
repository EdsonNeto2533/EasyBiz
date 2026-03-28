package com.mctable.easybiz.features.register_business.presentation.event

sealed class UpdateBusinessProfileEvent {
    data class UpdateBusiness(val id: String) : UpdateBusinessProfileEvent()
    data object OnBackPressed : UpdateBusinessProfileEvent()
    data object DismissErrorModal : UpdateBusinessProfileEvent()
    data class DescriptionChanged(val description: String) : UpdateBusinessProfileEvent()
    data class CellphoneChanged(val cellphone: String) : UpdateBusinessProfileEvent()
    data class MinimalPriceChanged(val price: String) : UpdateBusinessProfileEvent()
    data class YearsOfExperienceChanged(val years: String) : UpdateBusinessProfileEvent()
    data class ImageChanged(val image: ByteArray) : UpdateBusinessProfileEvent()
}
