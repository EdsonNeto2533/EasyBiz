package com.mctable.easybiz.features.auth.presentation.event

import com.mctable.easybiz.features.auth.presentation.state.OperationType
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController

sealed class VerifyEmailEvent {
    data object SendCode : VerifyEmailEvent()
    data class OnEmailTyped(val email: String) : VerifyEmailEvent()
    data object HideErrorDialog : VerifyEmailEvent()
}
