package com.mctable.easybiz.core.notification.data.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterFcmTokenRequest(
    val fcmToken: String
)