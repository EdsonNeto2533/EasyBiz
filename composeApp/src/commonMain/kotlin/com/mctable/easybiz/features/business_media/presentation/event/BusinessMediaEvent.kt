package com.mctable.easybiz.features.business_media.presentation.event

sealed class BusinessMediaEvent {
    data class LoadMedia(val businessId: String) : BusinessMediaEvent()
    data class AddMedia(val businessId: String, val fileBytes: ByteArray, val mimeType: String, val fileName: String) : BusinessMediaEvent()
    data class DeleteMedia(val businessId: String, val mediaId: String) : BusinessMediaEvent()
    data object OnBackPressed : BusinessMediaEvent()
    data object DismissError : BusinessMediaEvent()
}