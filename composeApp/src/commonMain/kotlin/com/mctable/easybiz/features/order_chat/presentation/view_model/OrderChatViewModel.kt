package com.mctable.easybiz.features.order_chat.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.order_chat.domain.usecase.DisconnectChatUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.GetOrderMessagesUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.MarkMessageAsReadUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.ObserveOrderMessagesUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.ObserveTypingStatusUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.SendOrderMessageUseCase
import com.mctable.easybiz.features.order_chat.domain.usecase.SendTypingStatusUseCase
import com.mctable.easybiz.features.order_chat.presentation.event.OrderChatEvent
import com.mctable.easybiz.features.order_chat.presentation.state.OrderChatState
import kotlinx.coroutines.launch

class OrderChatViewModel(
    private val getOrderMessagesUseCase: GetOrderMessagesUseCase,
    private val sendOrderMessageUseCase: SendOrderMessageUseCase,
    private val observeOrderMessagesUseCase: ObserveOrderMessagesUseCase,
    private val sendTypingStatusUseCase: SendTypingStatusUseCase,
    private val observeTypingStatusUseCase: ObserveTypingStatusUseCase,
    private val markMessageAsReadUseCase: MarkMessageAsReadUseCase,
    private val disconnectChatUseCase: DisconnectChatUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(OrderChatState())
        private set

    private var userName: String? = null

    fun onEvent(event: OrderChatEvent) {
        when (event) {
            is OrderChatEvent.Init -> {
                state = state.copy(orderId = event.orderId)
                loadMessages()
                observeMessages()
                observeTyping()
            }

            OrderChatEvent.OnSendMessage -> sendMessage()
            is OrderChatEvent.OnInputTextChanged -> {
                state = state.copy(inputText = event.text)
                handleTypingStatus()
            }

            OrderChatEvent.OnBackPressed -> navigator.pop()
            OrderChatEvent.OnLoadMoreMessages -> loadMoreMessages()
            OrderChatEvent.Disconnect -> viewModelScope.launch {
                disconnectChatUseCase.execute()
            }
        }
    }

    private fun loadMessages() {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            getOrderMessagesUseCase.execute(state.orderId, 0, 20).fold(
                onSuccess = { page ->
                    val receiverMessages = page.messages.filter { it.mine }
                    userName = receiverMessages.firstOrNull()?.senderName
                    state = state.copy(
                        messages = page.messages,
                        isLastPage = page.isLast,
                        isLoading = false
                    )
                },
                onFailure = {
                    state = state.copy(isLoading = false, isError = true)
                }
            )
        }
    }

    private fun loadMoreMessages() {
        if (state.isLastPage || state.isLoading) return

        state = state.copy(isLoading = true)
        viewModelScope.launch {
            val nextPage = state.currentPage + 1
            getOrderMessagesUseCase.execute(state.orderId, nextPage, 20).fold(
                onSuccess = { page ->
                    state = state.copy(
                        messages = state.messages + page.messages,
                        isLastPage = page.isLast,
                        currentPage = nextPage,
                        isLoading = false
                    )
                },
                onFailure = {
                    state = state.copy(isLoading = false, isError = true)
                }
            )
        }
    }

    private fun sendMessage() {
        if (state.inputText.isBlank()) return

        val content = state.inputText
        state = state.copy(inputText = "")

        viewModelScope.launch {
            sendOrderMessageUseCase.execute(state.orderId, content).fold(
                onSuccess = {},
                onFailure = {}
            )
        }
    }

    private fun handleTypingStatus() {
        viewModelScope.launch {
            userName?.let {
                sendTypingStatusUseCase.execute(state.orderId, it, state.inputText.isNotBlank())
            }
        }
    }

    private fun markAsRead(messageId: String) {
        val message = state.messages.find { it.id == messageId }
        if (message != null && !message.mine && !message.isRead) {
            viewModelScope.launch {
                markMessageAsReadUseCase.execute(state.orderId, messageId)
            }
        }
    }

    private fun observeMessages() {
        viewModelScope.launch {
            observeOrderMessagesUseCase.execute(state.orderId).collect { messageEntity ->
                if(messageEntity.mine){
                    userName = messageEntity.senderName
                }
                if (state.messages.none { it.id == messageEntity.id }) {
                    state = state.copy(
                        messages = listOf(messageEntity) + state.messages
                    )
                }
            }
        }
    }

    private fun observeTyping() {
        viewModelScope.launch {
            observeTypingStatusUseCase.execute(state.orderId).collect { typingDto ->
                state = state.copy(showTyping = typingDto.isTyping)
            }
        }
    }
}
