package com.mctable.easybiz.features.order_chat.domain.usecase

import com.mctable.easybiz.features.order_chat.domain.repository.OrderChatRepository

interface DisconnectChatUseCase {
    suspend fun execute(): Result<Unit>
}

class DisconnectUseCaseImpl(
    private val repository: OrderChatRepository
) : DisconnectChatUseCase {
    override suspend fun execute(): Result<Unit> {
        return repository.disconnect()
    }

}
