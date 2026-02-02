package com.mctable.easybiz.core.networking

sealed class NetworkException(message: String) : Throwable(message) {
    data class HttpError(val code: Int) :
        NetworkException("HTTP error $code")

    class NetworkUnavailable :
        NetworkException("Network unavailable")

    class Timeout :
        NetworkException("Request timeout")

    data class Unknown(val error: Throwable) :
        NetworkException(error.message ?: "Unknown error")
}
