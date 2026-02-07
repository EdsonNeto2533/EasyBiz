package com.mctable.easybiz.core.networking

interface EasyBizNetworking {

    suspend fun <T> get(
        host: String,
        path: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> post(
        host: String,
        path: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> put(
        host: String,
        path: String,
        body: Any? = null,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>

    suspend fun <T> delete(
        host: String,
        path: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap(),
        responseMapper: (String) -> T
    ): Result<T>
}

