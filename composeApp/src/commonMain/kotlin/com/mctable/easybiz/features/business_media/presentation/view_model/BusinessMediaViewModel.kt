package com.mctable.easybiz.features.business_media.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.business_media.domain.usecase.AddBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.domain.usecase.DeleteBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.domain.usecase.GetBusinessMediaUseCase
import com.mctable.easybiz.features.business_media.presentation.event.BusinessMediaEvent
import com.mctable.easybiz.features.business_media.presentation.state.BusinessMediaState
import kotlinx.coroutines.launch

class BusinessMediaViewModel(
    private val getBusinessMediaUseCase: GetBusinessMediaUseCase,
    private val addBusinessMediaUseCase: AddBusinessMediaUseCase,
    private val deleteBusinessMediaUseCase: DeleteBusinessMediaUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(BusinessMediaState())
        private set

    fun onEvent(event: BusinessMediaEvent) {
        when (event) {
            is BusinessMediaEvent.LoadMedia -> loadMedia(event.businessId)
            is BusinessMediaEvent.AddMedia -> addMedia(event.businessId, event.fileBytes, event.mimeType, event.fileName)
            is BusinessMediaEvent.DeleteMedia -> deleteMedia(event.businessId, event.mediaId)
            BusinessMediaEvent.OnBackPressed -> navigator.pop()
            BusinessMediaEvent.DismissError -> state = state.copy(isError = false, errorMessage = null)
        }
    }

    private fun loadMedia(businessId: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getBusinessMediaUseCase.execute(businessId)
                .onSuccess { state = state.copy(isLoading = false, isUploading = false, mediaList = it) }
                .onFailure { state = state.copy(isLoading = false, isUploading = false, isError = true, errorMessage = it.message) }
        }
    }

    private fun addMedia(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String) {
        state = state.copy(isUploading = true)
        viewModelScope.launch {
            addBusinessMediaUseCase.execute(businessId, fileBytes, mimeType, fileName)
                .onSuccess { loadMedia(businessId) }
                .onFailure { state = state.copy(isUploading = false, isError = true, errorMessage = it.message) }
        }
    }

    private fun deleteMedia(businessId: String, mediaId: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            deleteBusinessMediaUseCase.execute(businessId, mediaId)
                .onSuccess { loadMedia(businessId) }
                .onFailure { state = state.copy(isLoading = false, isError = true, errorMessage = it.message) }
        }
    }
}