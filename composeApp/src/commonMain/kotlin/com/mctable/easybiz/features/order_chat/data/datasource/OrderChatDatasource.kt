package com.mctable.easybiz.features.order_chat.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.order_chat.data.manager.OrderChatWebSocketManager
import com.mctable.easybiz.features.order_chat.data.mapper.OrderChatMapper
import com.mctable.easybiz.features.order_chat.data.model.OrderChatMessageResponseModel
import com.mctable.easybiz.features.order_chat.data.model.OrderChatPageResponseModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import org.hildan.krossbow.stomp.sendText
import org.hildan.krossbow.stomp.subscribeText

interface OrderChatDatasource {
    suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageResponseModel>

    suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<Unit>

    suspend fun observeMessages(orderId: String): Flow<OrderChatMessageResponseModel>

    suspend fun disconnect(): Result<Unit>
}

class OrderChatDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv,
    private val orderChatWebSocketManager: OrderChatWebSocketManager
) : OrderChatDatasource {

    override suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageResponseModel> {
        return networking.get(
            host = appEnv.host,
            path = "/pedidos/$orderId/mensagens",
            params = mapOf(
                "page" to page.toString(),
                "size" to size.toString()
            ),
            responseMapper = { jsonString ->
                OrderChatMapper.parsePageResponse(jsonString)
            }
        )
    }

    override suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<Unit> {
        val payload = """{"conteudo": "$content"}"""
        val session = orderChatWebSocketManager.getSession()
        session.sendText("/app/chat/$orderId", payload)

        return Result.success(Unit)
    }

    override suspend fun observeMessages(orderId: String): Flow<OrderChatMessageResponseModel> =
        flow {
            val session = orderChatWebSocketManager.getSession()

            val subscribeDestination = "/user/queue/chat/$orderId"

            val subscription = session.subscribeText(subscribeDestination)

            subscription.collect {
                emit(Json.decodeFromString(it))
            }
        }

    override suspend fun disconnect(): Result<Unit> {
        return Result.success(orderChatWebSocketManager.disconnect())
    }
}
