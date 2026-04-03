package com.mctable.easybiz.features.order_chat.data.datasource

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.features.order_chat.data.dto.SendMessageDto
import com.mctable.easybiz.features.order_chat.data.mapper.OrderChatMapper
import com.mctable.easybiz.features.order_chat.data.model.OrderChatMessageResponseModel
import com.mctable.easybiz.features.order_chat.data.model.OrderChatPageResponseModel
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map

interface OrderChatDatasource {
    suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageResponseModel>

    suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<OrderChatMessageResponseModel>

    fun observeMessages(orderId: String): Flow<OrderChatMessageResponseModel>
}

class OrderChatDatasourceImpl(
    private val networking: EasyBizNetworking,
    private val appEnv: AppEnv
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
    ): Result<OrderChatMessageResponseModel> {
        return networking.post(
            host = appEnv.host,
            path = "/pedidos/$orderId/mensagens",
            body = SendMessageDto(conteudo = content),
            responseMapper = { jsonString ->
                OrderChatMapper.parseMessageResponse(jsonString)
            }
        )
    }

    override fun observeMessages(orderId: String): Flow<OrderChatMessageResponseModel> = callbackFlow {
        networking.webSocket(
            host = appEnv.host,
            path = "/pedidos/$orderId/mensagens",
            block = {
                incoming.consumeAsFlow()
                    .filterIsInstance<Frame.Text>()
                    .map { frame ->
                        val jsonString = frame.readText()
                        OrderChatMapper.parseMessageResponse(jsonString)
                    }
                    .collect { message ->
                        send(message)
                    }
            }
        )
        close()
    }
}
