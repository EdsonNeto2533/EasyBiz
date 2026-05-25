package com.mctable.easybiz.features.order_chat.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.ChatBubbleMine
import com.mctable.easybiz.core.ds.theme.ChatBubbleTheirs
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.theme.Neutral50
import com.mctable.easybiz.core.ds.theme.OnlineGreen
import com.mctable.easybiz.core.ds.theme.SuccessGreen
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.order_chat.domain.entity.OrderChatMessageEntity
import com.mctable.easybiz.features.order_chat.presentation.event.OrderChatEvent
import com.mctable.easybiz.features.order_chat.presentation.state.OrderChatState

@Composable
fun OrderChatPage(
    state: OrderChatState,
    onEvent: (OrderChatEvent) -> Unit,
    orderId: String
) {
    val listState = rememberLazyListState()

    val shouldLoadMore by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItemsNumber = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            lastVisibleItemIndex > (totalItemsNumber - 5) && totalItemsNumber > 0
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore && !state.isLastPage && !state.isLoading) {
            onEvent(OrderChatEvent.OnLoadMoreMessages)
        }
    }

    DisposableEffect(Unit) {
        onEvent(OrderChatEvent.Init(orderId))

        onDispose {
            onEvent(OrderChatEvent.Disconnect)
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Chat do Pedido",
                showBackArrow = true,
                onBackClick = { onEvent(OrderChatEvent.OnBackPressed) }
            )
        },
        bottomBar = {
            Column {
                AnimatedVisibility(visible = state.showTyping) {
                    Text(
                        "Digitando...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.screenPaddingHorizontal,
                                vertical = Dimens.spacingXs
                            ),
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                ChatInputBar(
                    text = state.inputText,
                    onTextChanged = { onEvent(OrderChatEvent.OnInputTextChanged(it)) },
                    onSend = { onEvent(OrderChatEvent.OnSendMessage) },
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.screenPaddingHorizontal),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacingSm),
                reverseLayout = true
            ) {
                items(state.messages, key = { it.id }) { message ->
                    ChatMessageBubble(message = message)
                }
            }

            if (state.isLoading && state.messages.isEmpty()) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule(
                    description = state.errorMessage ?: "Erro desconhecido"
                ) {
                    onEvent(OrderChatEvent.Init(orderId))
                }
            }
        }
    }
}

@Composable
fun ChatMessageBubble(message: OrderChatMessageEntity) {
    val isMine = message.mine

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            if (!isMine) {
                AsyncImage(
                    model = message.senderPhotoUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(Dimens.avatarSizeSm)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.width(Dimens.spacingSm))
            }

            Surface(
                color = if (isMine) ChatBubbleMine else ChatBubbleTheirs,
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = if (isMine) 16.dp else 4.dp,
                    bottomEnd = if (isMine) 4.dp else 16.dp
                )
            ) {
                Column(modifier = Modifier.padding(Dimens.spacingMd)) {
                    if (!isMine) {
                        Text(
                            text = message.senderName,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(Dimens.spacingXs))
                    }
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = message.sentAt.substring(11, 16),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                        if (isMine && message.isRead) {
                            Spacer(Modifier.width(Dimens.spacingXs))
                            Icon(
                                painter = AppIcons.done(),
                                contentDescription = null,
                                tint = SuccessGreen,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatInputBar(
    text: String,
    onTextChanged: (String) -> Unit,
    onSend: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth().imePadding(),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.spacingMd,
                vertical = Dimens.spacingSm
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = onTextChanged,
                placeholder = {
                    Text(
                        "Escreva uma mensagem...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Neutral50,
                    unfocusedContainerColor = Neutral50,
                    disabledContainerColor = Neutral50,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = MaterialTheme.shapes.large
            )
            Spacer(Modifier.width(Dimens.spacingSm))
            Surface(
                shape = CircleShape,
                color = if (text.isNotBlank()) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.size(44.dp)
            ) {
                IconButton(
                    onClick = onSend,
                    enabled = text.isNotBlank()
                ) {
                    Icon(
                        painter = AppIcons.send(),
                        contentDescription = "Enviar",
                        tint = if (text.isNotBlank()) MaterialTheme.colorScheme.onPrimary
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OrderChatPagePreview() {
    val state = OrderChatState(
        messages = listOf(
            OrderChatMessageEntity(
                id = "1",
                orderId = "1",
                senderId = "user1",
                senderName = "João Silva",
                content = "Olá",
                sentAt = "2026-04-03T17:32:16.331Z",
                isRead = true,
                readAt = null,
                senderPhotoUrl = null,
                true,
            ),
            OrderChatMessageEntity(
                id = "1",
                orderId = "1",
                senderId = "user1",
                senderName = "João Silva",
                content = "Olá, gostaria de saber o status do pedido",
                sentAt = "2026-04-03T17:32:16.331Z",
                isRead = true,
                readAt = null,
                senderPhotoUrl = null,
                true
            ),
            OrderChatMessageEntity(
                id = "2",
                orderId = "1",
                senderId = "user2",
                senderName = "Marcos Elétrica",
                content = "Claro! Já estou a caminho.",
                sentAt = "2026-04-03T17:35:10.331Z",
                isRead = true,
                readAt = null,
                senderPhotoUrl = null,
                false
            )
        ),
        showTyping = true
    )

    EasyBizTheme {
        OrderChatPage(
            state = state,
            onEvent = {},
            orderId = "1"
        )
    }
}
