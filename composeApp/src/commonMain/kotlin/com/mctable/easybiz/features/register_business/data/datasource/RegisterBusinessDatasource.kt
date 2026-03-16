package com.mctable.easybiz.features.register_business.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.register_business.data.dto.CreateBusinessRequest
import com.mctable.easybiz.features.register_business.data.dto.UpdateProfileRequest
import com.mctable.easybiz.features.register_business.data.mapper.RegisterBusinessMapper
import com.mctable.easybiz.features.register_business.data.model.BusinessProfileResponseModel
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

interface RegisterBusinessDatasource {
    suspend fun createBusiness(request: CreateBusinessRequest): Result<BusinessProfileResponseModel>
    suspend fun updateProfile(id: Int, request: UpdateProfileRequest): Result<Unit>
    suspend fun addLogo(id: Int, imageBytes: ByteArray): Result<Unit>
}

class RegisterBusinessDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val networkingMultiPart: EasyBizNetworking,
    private val appEnv: AppEnv
) : RegisterBusinessDatasource {

    override suspend fun createBusiness(request: CreateBusinessRequest): Result<BusinessProfileResponseModel> {
        return networking.post(
            host = appEnv.host,
            path = "/negocios",
            body = request,
            responseMapper = { RegisterBusinessMapper.parseResponse(it) }
        )
    }

    override suspend fun updateProfile(id: Int, request: UpdateProfileRequest): Result<Unit> {
        return networking.patch(
            host = appEnv.host,
            path = "/negocios/$id/perfil",
            body = request,
            responseMapper = { }
        )
    }

    override suspend fun addLogo(id: Int, imageBytes: ByteArray): Result<Unit> {
        return networkingMultiPart.patch(
            host = appEnv.host,
            path = "/negocios/$id/logo",
            body = MultiPartFormDataContent(
                formData {
                    append(
                        key = "arquivo",
                        value = imageBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, "image/jpeg")
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=\"logo.jpg\""
                            )
                        }
                    )
                }
            ),
            responseMapper = { }
        )
    }
}
