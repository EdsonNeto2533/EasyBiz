package com.mctable.easybiz.features.order_chat.data.repository

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.features.order_chat.data.datasource.OrderChatDatasource
import com.mctable.easybiz.features.order_chat.data.mapper.OrderChatMapper
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatPageEntity
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OrderChatRepositoryImpl(
    private val datasource: OrderChatDatasource,
    private val easeBizStorage: EasyBizStorage
) : OrderChatRepository {

    override suspend fun getMessages(
        orderId: String,
        page: Int,
        size: Int
    ): Result<OrderChatPageEntity> = runCatching {
        val userId = easeBizStorage.getString("userId")
        return datasource.getMessages(orderId, page, size).map {
            OrderChatMapper.toPageEntity(it, userId ?: "")
        }

    }

    override suspend fun sendMessage(
        orderId: String,
        content: String
    ): Result<Unit> = runCatching {
        return datasource.sendMessage(orderId, content)
    }

    override suspend fun observeMessages(orderId: String): Flow<OrderChatMessageEntity> {
        val userId = easeBizStorage.getString("userId")
        return datasource.observeMessages(orderId).map { response ->
            OrderChatMapper.toEntity(response, userId ?: "")
        }
    }

    override suspend fun disconnect(): Result<Unit> = runCatching {
        return datasource.disconnect()
    }
}
