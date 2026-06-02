package com.mctable.easybiz.features.business_media.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.business_media.data.mapper.BusinessMediaMapper
import com.mctable.easybiz.features.business_media.data.model.BusinessMediaResponseModel
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders

interface BusinessMediaDatasource {
    suspend fun getMedia(businessId: String): Result<List<BusinessMediaResponseModel>>
    suspend fun addMedia(businessId: String, fileBytes: ByteArray, mimeType: String, fileName: String): Result<Unit>
    suspend fun deleteMedia(businessId: String, mediaId: String): Result<Unit>
}

class BusinessMediaDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val networkingMultiPart: EasyBizNetworking,
    private val appEnv: AppEnv
) : BusinessMediaDatasource {

    override suspend fun getMedia(businessId: String): Result<List<BusinessMediaResponseModel>> =
        networking.get(
            host = appEnv.host,
            path = "/negocios/$businessId/midia",
            responseMapper = { BusinessMediaMapper.parseListResponse(it) }
        )

    override suspend fun addMedia(
        businessId: String,
        fileBytes: ByteArray,
        mimeType: String,
        fileName: String
    ): Result<Unit> =
        networkingMultiPart.post(
            host = appEnv.host,
            path = "/negocios/$businessId/midia",
            body = MultiPartFormDataContent(
                formData {
                    append(
                        key = "arquivo",
                        value = fileBytes,
                        headers = Headers.build {
                            append(HttpHeaders.ContentType, mimeType)
                            append(HttpHeaders.ContentDisposition, "filename=\"$fileName\"")
                        }
                    )
                }
            ),
            responseMapper = { }
        )

    override suspend fun deleteMedia(businessId: String, mediaId: String): Result<Unit> =
        networking.delete(
            host = appEnv.host,
            path = "/negocios/$businessId/midia/$mediaId",
            responseMapper = { }
        )
}