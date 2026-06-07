package com.mctable.easybiz.features.user_data.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.core.navigation.UserData
import com.mctable.easybiz.core.navigation.userChannel
import com.mctable.easybiz.features.auth.domain.usecase.DeleteAccountUseCase
import com.mctable.easybiz.features.auth.domain.usecase.LogoutUseCase
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
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val logoutUseCase: LogoutUseCase,
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
            is UserDataEvent.UploadPhotoFromViewer -> uploadPhotoFromViewer(event.bytes)
            UserDataEvent.UpdateUserData -> updateUserData()
            UserDataEvent.OnBackPressed -> navigator.pop()
            UserDataEvent.ShowDeleteConfirmation -> state = state.copy(
                showDeleteConfirmation = true,
                deleteConfirmationStep = 1,
                deleteConfirmationInput = ""
            )
            UserDataEvent.DismissDeleteConfirmation -> state = state.copy(
                showDeleteConfirmation = false,
                deleteConfirmationStep = 1,
                deleteConfirmationInput = ""
            )
            UserDataEvent.AdvanceDeleteStep -> state = state.copy(deleteConfirmationStep = 2)
            is UserDataEvent.OnDeleteConfirmationInputChanged -> state = state.copy(deleteConfirmationInput = event.input)
            UserDataEvent.ConfirmDeleteAccount -> deleteAccount()
            UserDataEvent.Logout -> logout()
        }
    }

    private fun deleteAccount() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            deleteAccountUseCase.execute().fold(
                onSuccess = {
                    navigator.popUpTo(Destination.Login)
                },
                onFailure = { error ->
                    state = state.copy(error = error.message, isLoading = false)
                }
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            logoutUseCase.execute().fold(
                onSuccess = {
                    navigator.popUpTo(Destination.Login)
                },
                onFailure = { error ->
                    state = state.copy(error = error.message, isLoading = false)
                }
            )
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
                    userChannel.send(UserData(user.id, user.name, user.email, user.photoUrl))
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
                    state.updatedImageBytes?.let {
                        updateUserImage()
                    } ?: run {
                        state = state.copy(
                            user = user,
                            isEditMode = false,
                            isLoading = false
                        )
                    }

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
                    navigator.pop()
                }
                .onFailure { error ->
                    state = state.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
        }
    }

    private fun uploadPhotoFromViewer(bytes: ByteArray) {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            updateUserPhotoUseCase.execute(bytes)
                .onSuccess {
                    loadUserData()
                }
                .onFailure { error ->
                    state = state.copy(error = error.message, isLoading = false)
                }
        }
    }
}
