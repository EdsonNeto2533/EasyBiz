package com.mctable.easybiz.core.networking.models

import kotlinx.serialization.Serializable

@Serializable
data class DefaultErrorModel(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String
)