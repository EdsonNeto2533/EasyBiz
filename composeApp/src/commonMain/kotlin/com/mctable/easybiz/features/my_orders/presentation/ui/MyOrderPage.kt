package com.mctable.easybiz.features.my_orders.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.molecules.BusinessInfoCardMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.features.my_orders.domain.entity.MyOrderEntity
import com.mctable.easybiz.features.my_orders.domain.enums.OrderStatus
import com.mctable.easybiz.features.my_orders.presentation.event.MyOrderEvent
import com.mctable.easybiz.features.my_orders.presentation.state.MyOrderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrderPage(
    state: MyOrderState,
    onEvent: (MyOrderEvent) -> Unit,
    paper: String? = null,
    businessId: String? = null
) {
    val listState = rememberLazyListState()
    val bottomSheetState = rememberModalBottomSheetState()

    LaunchedEffect(Unit) {
        onEvent(MyOrderEvent.GetMyOrders(paper, businessId))
    }

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
            onEvent(MyOrderEvent.LoadNextPage(paper, businessId))
        }
    }

    Scaffold(
        topBar = {
            TopAppBarOrganism(
                title = "Meus pedidos",
                showBackArrow = true,
                onBackClick = { onEvent(MyOrderEvent.OnBackPressed) }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.orders.size) { index ->
                    val order = state.orders[index]
                    BusinessInfoCardMolecule(
                        title = order.businessName,
                        subtitle = order.status.name,
                        logoUrl = order.businessLogoUrl,
                        onClick = {
                            if (order.status == OrderStatus.ABERTO || order.status == OrderStatus.ACEITO) {
                                onEvent(MyOrderEvent.OnOrderClick(order.id))
                            }
                        },
                        extraContent = {
                            if (businessId != null && (order.status == OrderStatus.ABERTO || order.status == OrderStatus.ACEITO)) {
                                Spacer(Modifier.height(8.dp))
                                TextButton(
                                    onClick = { onEvent(MyOrderEvent.OnUpdateStatusClick(order.id, order.status)) },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Atualizar status")
                                }
                            }
                        },
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                    )
                }
            }

            if (state.isLoading && state.orders.isEmpty()) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule {
                    onEvent.invoke(MyOrderEvent.GetMyOrders(paper, businessId))
                }
            }
        }

        if (state.showStatusBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { onEvent(MyOrderEvent.OnDismissBottomSheet) },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "Alterar status do pedido",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    
                    state.availableStatusOptions.forEach { status ->
                        Button(
                            onClick = { 
                                state.selectedOrderId?.let { id ->
                                    onEvent(MyOrderEvent.OnStatusSelected(id, status))
                                }
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                        ) {
                            Text(status.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MyOrderPagePreview() {
    val state = MyOrderState(
        orders = listOf(
            MyOrderEntity(
                id = "",
                clientId = "",
                clientName = "Ana Souza",
                businessId = "",
                businessName = "Marcos Elétrica",
                description = "Instalar chuveiro",
                desiredDate = "2026-03-21T15:08:39.551Z",
                status = OrderStatus.RECUSADO,
                createdAt = "2026-03-21T15:08:39.551Z",
                businessLogoUrl = "https://res.cloudinary.com/easybiz/image/upload/logo.jpg"
            ),
            MyOrderEntity(
                id = "",
                clientId = "",
                clientName = "Ana Souza",
                businessId = "",
                businessName = "Marcos Elétrica",
                description = "Instalar chuveiro",
                desiredDate = "2026-03-21T15:08:39.551Z",
                status = OrderStatus.ABERTO,
                createdAt = "2026-03-21T15:08:39.551Z",
                businessLogoUrl = "https://res.cloudinary.com/easybiz/image/upload/logo.jpg"
            )
        )
    )

    EasyBizTheme {
        MyOrderPage(
            state = state,
            onEvent = {}
        )
    }
}
