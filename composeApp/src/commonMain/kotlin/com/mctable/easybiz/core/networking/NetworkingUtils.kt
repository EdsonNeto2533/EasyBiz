package com.mctable.easybiz.core.networking

import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import io.ktor.client.plugins.*
import kotlinx.coroutines.TimeoutCancellationException


suspend fun <T> HttpResponse.mapTo(
    mapper: (String) -> T
): T {
    val body = bodyAsText()

    if (!status.isSuccess()) {
        throw NetworkException.HttpError(status.value)
    }

    return mapper(body)
}

suspend fun <T> safeRequest(
    block: suspend () -> T
): Result<T> {
    return try {
        Result.success(block())
    } catch (e: ResponseException) {
        Result.failure(
            NetworkException.HttpError(
                e.response.status.value,
            )
        )
    } catch (e: TimeoutCancellationException) {
        Result.failure(NetworkException.Timeout())
    } catch (e: Exception) {
        Result.failure(NetworkException.Unknown(e))
    }
}

