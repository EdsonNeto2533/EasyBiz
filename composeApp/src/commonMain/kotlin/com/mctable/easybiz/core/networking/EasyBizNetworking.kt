package com.mctable.easybiz.core.networking

interface EasyBizNetworking {

    suspend fun <T> get(
        url: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> post(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> put(
        url: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> delete(
        url: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>
}

