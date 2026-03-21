package com.mctable.easybiz.features.my_orders.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.molecules.BusinessInfoCardMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.features.my_orders.presentation.event.MyOrderEvent
import com.mctable.easybiz.features.my_orders.presentation.state.MyOrderState

@Composable
fun MyOrderPage(
    state: MyOrderState,
    onEvent: (MyOrderEvent) -> Unit
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
            onEvent(MyOrderEvent.LoadNextPage)
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
                        subtitle = order.status,
                        logoUrl = order.businessLogoUrl,
                        onClick = { /* Do nothing for now */ },
                        extraContent = {
                           // Future info here
                        }
                    )
                }
            }

            if (state.isLoading && state.orders.isEmpty()) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule {
                    onEvent.invoke(MyOrderEvent.GetMyOrders)
                }
            }
        }
    }
}
