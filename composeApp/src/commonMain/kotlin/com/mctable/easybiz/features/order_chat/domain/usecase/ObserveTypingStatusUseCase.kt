package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.data.dto.TypingStatusDto
import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository
import kotlinx.coroutines.flow.Flow

interface ObserveTypingStatusUseCase {
    suspend fun execute(orderId: String): Flow<TypingStatusDto>
}

class ObserveTypingStatusUseCaseImpl(
    private val repository: OrderChatRepository
) : ObserveTypingStatusUseCase {
    override suspend fun execute(orderId: String): Flow<TypingStatusDto> {
        return repository.observeTypingStatus(orderId)
    }
}
