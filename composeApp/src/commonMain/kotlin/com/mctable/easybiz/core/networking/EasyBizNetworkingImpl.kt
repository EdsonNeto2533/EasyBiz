package com.mctable.easybiz.core.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url

class EasyBizNetworkingImpl(
    private val httpClient: HttpClient
) : EasyBizNetworking {

    override suspend fun <T> get(
        host: String,
        path: String,
        headers: Map<String, String>,
        params: Map<String, String>,
        responseMapper: (String) -> T
    ): Result<T> = safeRequest {
        httpClient.get {
            url(getUrl(host,path))
            params.forEach {
                parameter(it.key, it.value)
            }
            headers.forEach {
                header(it.key, it.value)
            }
        }.mapTo(responseMapper)
    }

    override suspend fun <T> post(
        host: String,
        path: String,
        body: Any?,
        headers: Map<String, String>,
        params: Map<String, String>,
        responseMapper: (String) -> T
    ): Result<T> = safeRequest {
        httpClient.post {
            url(getUrl(host,path))
            params.forEach {
                parameter(it.key, it.value)
            }
            headers.forEach {
                header(it.key, it.value)
            }
            setBody(body)
        }.mapTo(responseMapper)
    }

    override suspend fun <T> put(
        host: String,
        path: String,
        body: Any?,
        headers: Map<String, String>,
        params: Map<String, String>,
        responseMapper: (String) -> T
    ): Result<T> = safeRequest {
        httpClient.put {
            url(getUrl(host,path))
            params.forEach {
                parameter(it.key, it.value)
            }
            headers.forEach {
                header(it.key, it.value)
            }
            setBody(body)
        }.mapTo(responseMapper)
    }

    override suspend fun <T> delete(
        host: String,
        path: String,
        headers: Map<String, String>,
        params: Map<String, String>,
        responseMapper: (String) -> T
    ): Result<T> = safeRequest {
        httpClient.delete {
            url(getUrl(host,path))
            params.forEach {
                parameter(it.key, it.value)
            }
            headers.forEach {
                header(it.key, it.value)
            }

        }.mapTo(responseMapper)
    }
    
    private fun getUrl(host: String, path: String) = "$host$path"

    
}