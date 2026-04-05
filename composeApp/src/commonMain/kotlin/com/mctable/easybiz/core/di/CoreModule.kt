package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.config.AppEnvImpl
import com.mctable.easybiz.core.location.LocationProvider
import com.mctable.easybiz.core.location.LocationProviderImpl
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.core.navigation.NavigatorImpl
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.core.networking.EasyBizNetworkingImpl
import com.mctable.easybiz.core.networking.EasyBizWebSocket
import com.mctable.easybiz.core.networking.EasyBizWebSocketImpl
import com.mctable.easybiz.core.networking.HttpClientFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.websocket.ktor.KtorWebSocketClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

val coreModule = module {
    single<EasyBizNetworking> {
        EasyBizNetworkingImpl(HttpClientFactory.build(get()))
    }
    single<EasyBizNetworking>(named("multiPart")) {
        EasyBizNetworkingImpl(HttpClientFactory.buildMultiPart(get()))
    }
    single<Navigator> { NavigatorImpl() }
    single<AppEnv> { AppEnvImpl() }
    single<LocationProvider> {
        LocationProviderImpl()
    }

    single<EasyBizWebSocket> {
        EasyBizWebSocketImpl(
            StompClient(KtorWebSocketClient(
                HttpClient {
                    install(Logging) {
                        level = LogLevel.ALL
                        logger = object : Logger {
                            override fun log(message: String) {
                                println(message)
                            }
                        }
                    }

                    install(WebSockets)
                }
            )),
            get()
        )
    }
}
