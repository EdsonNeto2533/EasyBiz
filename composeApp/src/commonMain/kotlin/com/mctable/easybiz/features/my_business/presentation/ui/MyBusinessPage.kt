package com.mctable.easybiz.features.my_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.my_business.domain.entity.MyBusinessEntity
import com.mctable.easybiz.features.my_business.presentation.event.MyBusinessEvent
import com.mctable.easybiz.features.my_business.presentation.state.MyBusinessState
import com.mctable.easybiz.features.my_business.presentation.ui.components.MyBusinessCard

@Composable
fun MyBusinessPage(
    state: MyBusinessState,
    onEvent: (MyBusinessEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        onEvent.invoke(MyBusinessEvent.GetMyBusiness)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Meus negócios",
                showBackArrow = true,
                onBackClick = { onEvent(MyBusinessEvent.OnBackPressed) }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.myBusinessList.isEmpty() && !state.isLoading -> {
                    EmptyStateMolecule(
                        icon = AppIcons.creditCard(),
                        title = "Nenhum negócio cadastrado",
                        description = "Cadastre sua empresa para começar a receber pedidos"
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(Dimens.screenPaddingHorizontal),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
                    ) {
                        items(state.myBusinessList.size) { index ->
                            val business = state.myBusinessList[index]
                            MyBusinessCard(
                                business = business,
                                onClick = {
                                    onEvent(MyBusinessEvent.OnBusinessClicked(business.id))
                                },
                                onEditClick = {
                                    onEvent(MyBusinessEvent.OnEditBusinessClicked(business.id))
                                },
                                onOrdersClick = {
                                    onEvent(MyBusinessEvent.OnViewOrdersClicked(business.id))
                                }
                            )
                        }
                    }
                }
            }

            if (state.isLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule(
                    description = state.errorMessage ?: "Erro desconhecido",
                ) {
                    onEvent.invoke(MyBusinessEvent.GetMyBusiness)
                }
            }
        }
    }
}

@Preview
@Composable
fun MyBusinessPagePreview() {
    val state = MyBusinessState(
        myBusinessList = listOf(
            MyBusinessEntity(
                id = "",
                name = "Oficina do Ricardo",
                category = "MECANICO",
                userId = "",
                userName = "Ricardo Silva",
                active = true,
                latitude = -23.5505,
                longitude = -46.6333,
                completeAddress = "Rua Teste, 123",
                averageRating = 4.8,
                logoUrl = null,
                distanceKm = 0.0,
                description = null,
                telephone = null,
                minimumPrice = null,
                yearsOfExperience = null,
                workingHours = null,
                totalReviews = null,
                totalCompletedOrders = null,
                highlightReview = null,
                highlightReviewAuthor = null,
                isFavorited = false
            )
        )
    )

    EasyBizTheme {
        MyBusinessPage(
            state = state,
            onEvent = {}
        )
    }
}
