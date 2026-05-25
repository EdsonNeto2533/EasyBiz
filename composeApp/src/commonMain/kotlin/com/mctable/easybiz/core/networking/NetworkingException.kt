package com.mctable.easybiz.core.networking

import com.mctable.easybiz.core.networking.models.DefaultErrorModel

sealed class NetworkException(message: String) : Throwable(message) {
    data class HttpError(
        val code: Int,
        val errorModel: DefaultErrorModel? = null
    ) : NetworkException(errorModel?.message ?: "HTTP error $code")

    class NetworkUnavailable :
        NetworkException("Network unavailable")

    class Timeout :
        NetworkException("Request timeout")

    data class Unknown(val error: Throwable) :
        NetworkException(error.message ?: "Unknown error")
}
