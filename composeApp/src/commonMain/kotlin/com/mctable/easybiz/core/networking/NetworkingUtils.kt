package com.mctable.easybiz.core.networking

import com.mctable.easybiz.core.networking.models.DefaultErrorModel
import io.ktor.client.statement.*
import io.ktor.http.isSuccess
import io.ktor.client.plugins.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.json.Json


private val json = Json { ignoreUnknownKeys = true }

suspend fun <T> HttpResponse.mapTo(
    mapper: (String) -> T
): T {
    val body = bodyAsText()

    if (!status.isSuccess()) {
        val errorModel = try {
            json.decodeFromString<DefaultErrorModel>(body)
        } catch (e: Exception) {
            null
        }
        throw NetworkException.HttpError(status.value, errorModel)
    }

    return mapper(body)
}

suspend fun <T> safeRequest(
    block: suspend () -> T
): Result<T> {
    return try {
        Result.success(block())
    } catch (e: ResponseException) {
        val body = try {
            e.response.bodyAsText()
        } catch (e: Exception) {
            null
        }
        val errorModel = body?.let {
            try {
                json.decodeFromString<DefaultErrorModel>(it)
            } catch (e: Exception) {
                null
            }
        }
        Result.failure(
            NetworkException.HttpError(
                e.response.status.value,
                errorModel
            )
        )
    } catch (e: TimeoutCancellationException) {
        Result.failure(NetworkException.Timeout())
    } catch (e: Exception) {
        Result.failure(NetworkException.Unknown(e))
    }
}
