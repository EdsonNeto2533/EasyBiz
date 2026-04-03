package com.mctable.easybiz.core.networking

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.core.networking.plugins.AuthTokenPlugin
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

object HttpClientFactory {

    fun build(
        easyBizStorage: EasyBizStorage
    ): HttpClient = HttpClient() {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(AuthTokenPlugin) {
            storage = easyBizStorage
        }

        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30.seconds.inWholeMilliseconds
            connectTimeoutMillis = 30.seconds.inWholeMilliseconds
            socketTimeoutMillis = 30.seconds.inWholeMilliseconds
        }

        install(WebSockets)

        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                explicitNulls = true
            })
        }

    }

    fun buildMultiPart(
        easyBizStorage: EasyBizStorage
    ): HttpClient = HttpClient() {
        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    println(message)
                }
            }
        }

        install(AuthTokenPlugin) {
            storage = easyBizStorage
        }

        defaultRequest {
            contentType(ContentType.MultiPart.FormData)
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30.seconds.inWholeMilliseconds
            connectTimeoutMillis = 30.seconds.inWholeMilliseconds
            socketTimeoutMillis = 30.seconds.inWholeMilliseconds
        }

    }
}