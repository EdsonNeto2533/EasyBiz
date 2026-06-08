package com.mctable.easybiz.features.order_chat.data.manager

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizWebSocket
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.hildan.krossbow.stomp.StompSession

class OrderChatWebSocketManager(
    private val easyBizWebSocket: EasyBizWebSocket,
    private val appEnv: AppEnv
) {

    private var session: StompSession? = null
    private val mutex = Mutex()

    suspend fun getSession(): StompSession = mutex.withLock {
        session ?: connect()
    }

    // Múltiplos flows podem falhar ao mesmo tempo — o mutex garante que só um reconecta
    suspend fun resetAndReconnect(): StompSession = mutex.withLock {
        runCatching { session?.disconnect() }
        session = null
        connect()
    }

    private suspend fun connect(): StompSession {
        val newSession = easyBizWebSocket.connect(appEnv.socketHost)
        session = newSession
        return newSession
    }

    suspend fun disconnect() = mutex.withLock {
        session?.disconnect()
        session = null
    }
}