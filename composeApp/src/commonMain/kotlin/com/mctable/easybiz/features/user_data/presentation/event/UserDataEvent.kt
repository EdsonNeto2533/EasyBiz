package com.mctable.easybiz.features.user_data.presentation.event

sealed interface UserDataEvent {
    data object LoadUserData : UserDataEvent
    data object TurnOnEditMode : UserDataEvent
    data class OnNameChanged(val name: String) : UserDataEvent
    data class OnImageLoaded(val bytes: ByteArray) : UserDataEvent
    data class UploadPhotoFromViewer(val bytes: ByteArray) : UserDataEvent
    data object UpdateUserData : UserDataEvent
    data object OnBackPressed : UserDataEvent
    data object ShowDeleteConfirmation : UserDataEvent
    data object DismissDeleteConfirmation : UserDataEvent
    data object AdvanceDeleteStep : UserDataEvent
    data class OnDeleteConfirmationInputChanged(val input: String) : UserDataEvent
    data object ConfirmDeleteAccount : UserDataEvent
    data object Logout : UserDataEvent
}
