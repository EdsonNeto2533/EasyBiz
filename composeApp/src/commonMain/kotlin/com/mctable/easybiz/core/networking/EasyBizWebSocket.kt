package com.mctable.easybiz.core.networking

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import org.hildan.krossbow.stomp.StompClient
import org.hildan.krossbow.stomp.StompSession

interface EasyBizWebSocket {
    suspend fun connect(url: String): StompSession
}

class EasyBizWebSocketImpl(
    private val client: StompClient,
    private val easyBizStorage: EasyBizStorage
) : EasyBizWebSocket {

    override suspend fun connect(url: String): StompSession {
        val headers = mutableMapOf<String, String>()
        val token = easyBizStorage.getString("token")
        token?.let {
            headers["Authorization"] = "Bearer $token"
        }

        return client.connect(url, customStompConnectHeaders = headers)
    }
}
