package com.mctable.easybiz.features.user_data.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.user_data.data.dto.UpdateUserRequest
import com.mctable.easybiz.features.user_data.data.mapper.UserDataMapper
import com.mctable.easybiz.features.user_data.data.model.UserDataResponseModel
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

interface UserDataDatasource {
    suspend fun getUserData(): Result<UserDataResponseModel>
    suspend fun updateUserData(request: UpdateUserRequest): Result<UserDataResponseModel>
    suspend fun updateUserPhoto(imageBytes: ByteArray): Result<Unit>
}

class UserDataDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val networkingMultiPart: EasyBizNetworking,
    private val appEnv: AppEnv
) : UserDataDatasource {

    override suspend fun getUserData(): Result<UserDataResponseModel> {
        return networking.get(
            host = appEnv.host,
            path = "/usuarios/me",
            responseMapper = { UserDataMapper.parseResponse(it) }
        )
    }

    override suspend fun updateUserData(request: UpdateUserRequest): Result<UserDataResponseModel> {
        return networking.patch(
            host = appEnv.host,
            path = "/usuarios/me",
            body = request,
            responseMapper = { UserDataMapper.parseResponse(it) }
        )
    }

    override suspend fun updateUserPhoto(imageBytes: ByteArray): Result<Unit> {
        return networkingMultiPart.patch(
            host = appEnv.host,
            path = "/usuarios/me/foto",
            body = MultiPartFormDataContent(
                formData {
                    append(
                        key = "arquivo",
                        value = imageBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"foto.jpg\""
                            )
                        }
                    )
                }
            ),
            responseMapper = { }
        )
    }
}
