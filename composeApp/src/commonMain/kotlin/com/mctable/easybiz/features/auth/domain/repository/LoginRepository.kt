package com.mctable.easybiz.features.auth.domain.repository

import com.mctable.easybiz.features.auth.data.model.VerifyEmailResponseModel
import com.mctable.easybiz.features.auth.data.request.ResetPasswordRequest
import com.mctable.easybiz.features.auth.domain.entity.LoginEntity

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<LoginEntity>
    suspend fun register(
        email: String,
        password: String,
        name: String,
        registerToken: String
    ): Result<LoginEntity>

    suspend fun verifyEmail(email: String, code: String): Result<VerifyEmailResponseModel>
    suspend fun sendCode(email: String): Result<Unit>
    suspend fun logout(refreshToken: String): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
    suspend fun forgetPassword(email: String): Result<Unit>
    suspend fun resetPassword(request: ResetPasswordRequest): Result<Unit>
}
