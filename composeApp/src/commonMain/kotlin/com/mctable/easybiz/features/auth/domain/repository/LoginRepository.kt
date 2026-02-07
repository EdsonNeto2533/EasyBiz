package com.mctable.easybiz.features.auth.domain.repository

import com.mctable.easybiz.features.auth.domain.entity.LoginEntity

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<LoginEntity>
}
