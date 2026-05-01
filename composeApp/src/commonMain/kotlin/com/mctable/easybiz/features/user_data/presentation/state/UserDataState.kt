package com.mctable.easybiz.features.user_data.presentation.state

import com.mctable.easybiz.features.user_data.domain.entity.UserEntity

data class UserDataState(
    val user: UserEntity? = null,
    val isLoading: Boolean = false,
    val isEditMode: Boolean = false,
    val updatedName: String = "",
    val updatedImageBytes: ByteArray? = null,
    val error: String? = null
)
