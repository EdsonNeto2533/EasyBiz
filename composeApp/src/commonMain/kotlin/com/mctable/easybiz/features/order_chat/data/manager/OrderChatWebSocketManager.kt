package com.mctable.easybiz.features.order_chat.data.manager

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizWebSocket
import org.hildan.krossbow.stomp.StompSession

class OrderChatWebSocketManager(
    private val easyBizWebSocket: EasyBizWebSocket,
    private val appEnv: AppEnv
) {

    private var session: StompSession? = null

    suspend fun getSession(): StompSession {
        return session ?: connect()
    }

    private suspend fun connect(): StompSession {
        val newSession = easyBizWebSocket.connect(
            appEnv.socketHost
        )
        session = newSession
        return newSession
    }

    suspend fun disconnect() {
        session?.disconnect()
        session = null
    }
}