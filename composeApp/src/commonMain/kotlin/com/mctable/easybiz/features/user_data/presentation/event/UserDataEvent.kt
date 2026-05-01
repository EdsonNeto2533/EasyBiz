package com.mctable.easybiz.features.user_data.presentation.event

sealed interface UserDataEvent {
    data object LoadUserData : UserDataEvent
    data object TurnOnEditMode : UserDataEvent
    data class OnNameChanged(val name: String) : UserDataEvent
    data class OnImageLoaded(val bytes: ByteArray) : UserDataEvent
    data object UpdateUserData : UserDataEvent
    data object UpdateUserImage : UserDataEvent
    data object OnBackPressed : UserDataEvent
}
