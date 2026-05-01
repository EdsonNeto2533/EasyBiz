package com.mctable.easybiz.features.user_data.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.core.navigation.userChannel
import com.mctable.easybiz.features.user_data.domain.usecase.GetUserDataUseCase
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserDataUseCase
import com.mctable.easybiz.features.user_data.domain.usecase.UpdateUserPhotoUseCase
import com.mctable.easybiz.features.user_data.presentation.event.UserDataEvent
import com.mctable.easybiz.features.user_data.presentation.state.UserDataState
import kotlinx.coroutines.launch

class UserDataViewModel(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(UserDataState())
        private set

    fun onEvent(event: UserDataEvent) {
        when (event) {
            UserDataEvent.LoadUserData -> loadUserData()
            UserDataEvent.TurnOnEditMode -> turnOnEditMode()
            is UserDataEvent.OnNameChanged -> onNameChanged(event.name)
            is UserDataEvent.OnImageLoaded -> onImageLoaded(event.bytes)
            UserDataEvent.UpdateUserData -> updateUserData()
            UserDataEvent.UpdateUserImage -> updateUserImage()
            UserDataEvent.OnBackPressed -> navigator.pop()
        }
    }

    private fun loadUserData() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            getUserDataUseCase.execute()
                .onSuccess { user ->
                    state = state.copy(
                        user = user,
                        updatedName = user.name,
                        isLoading = false
                    )
                    userChannel.send(user.name)
                }
                .onFailure { error ->
                    state = state.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
        }
    }

    private fun turnOnEditMode() {
        state = state.copy(isEditMode = !state.isEditMode)
    }

    private fun onNameChanged(name: String) {
        state = state.copy(updatedName = name)
    }

    private fun onImageLoaded(bytes: ByteArray) {
        state = state.copy(updatedImageBytes = bytes)
    }

    private fun updateUserData() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            updateUserDataUseCase.execute(state.updatedName)
                .onSuccess { user ->
                    state = state.copy(
                        user = user,
                        isEditMode = false,
                        isLoading = false
                    )
                    userChannel.send(user.name)
                }
                .onFailure { error ->
                    state = state.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
        }
    }

    private fun updateUserImage() {
        val bytes = state.updatedImageBytes ?: return
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            updateUserPhotoUseCase.execute(bytes)
                .onSuccess {
                    loadUserData()
                }
                .onFailure { error ->
                    state = state.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
        }
    }
}
