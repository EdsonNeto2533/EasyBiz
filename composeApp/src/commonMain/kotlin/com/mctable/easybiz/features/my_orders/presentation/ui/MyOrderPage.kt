package com.mctable.easybiz.features.my_orders.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
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
        containerColor = MaterialTheme.colorScheme.background,
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
            if (state.orders.isEmpty() && !state.isLoading) {
                EmptyStateMolecule(
                    icon = AppIcons.search(),
                    title = "Nenhum pedido encontrado",
                    description = "Seus pedidos aparecerão aqui"
                )
            } else {
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
                                    TextButton(
                                        onClick = { onEvent(MyOrderEvent.OnUpdateStatusClick(order.id, order.status)) },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = Dimens.cardPadding)
                                    ) {
                                        Text(
                                            "Atualizar status",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(
                                top = Dimens.spacingMd,
                                start = Dimens.screenPaddingHorizontal,
                                end = Dimens.screenPaddingHorizontal
                            )
                        )
                    }
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
                sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = Dimens.spacing3xl, start = Dimens.screenPaddingHorizontal, end = Dimens.screenPaddingHorizontal)
                ) {
                    Text(
                        text = "Alterar status do pedido",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = Dimens.spacingLg)
                    )

                    HorizontalDivider(
                        thickness = Dimens.dividerThickness,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Spacer(Modifier.height(Dimens.spacingLg))

                    state.availableStatusOptions.forEach { status ->
                        Button(
                            onClick = {
                                state.selectedOrderId?.let { id ->
                                    onEvent(MyOrderEvent.OnStatusSelected(id, status))
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = Dimens.spacingXs),
                            shape = MaterialTheme.shapes.medium,
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 0.dp
                            )
                        ) {
                            Text(
                                status.name,
                                style = MaterialTheme.typography.labelLarge
                            )
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
        availableStatusOptions = listOf(OrderStatus.ACEITO, OrderStatus.RECUSADO),
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
            onEvent = {},
            businessId = "1"
        )
    }
}
