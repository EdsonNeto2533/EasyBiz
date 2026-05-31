package com.mctable.easybiz.features.auth.data.request

import kotlinx.serialization.Serializable

@Serializable
data class ForgetPasswordRequest(
    val email: String
)
