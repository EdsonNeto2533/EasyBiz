package com.mctable.easybiz.features.auth.domain.entity

data class LoginEntity(
    val email: String,
    val name: String?,
    val photoUrl: String?
)
