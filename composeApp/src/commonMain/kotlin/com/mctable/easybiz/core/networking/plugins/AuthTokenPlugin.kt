package com.mctable.easybiz.core.networking.plugins
import com.mctable.easybiz.core.local_storage.EasyBizStorage
import io.ktor.client.plugins.api.*

class AuthTokenConfig {
    lateinit var storage: EasyBizStorage
}

val AuthTokenPlugin = createClientPlugin(
    name = "AuthTokenPlugin",
    createConfiguration = ::AuthTokenConfig
) {

    val storage = pluginConfig.storage

    onRequest { request, _ ->

        val token = storage.getString("token")

        if (!token.isNullOrBlank()) {
            request.headers.append(
                "Authorization",
                "Bearer $token"
            )
        }
    }
}