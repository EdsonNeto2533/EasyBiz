package com.mctable.easybiz.features.my_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
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

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Você ainda não possui negócios cadastrados",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                else -> {

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        items(state.myBusinessList.size) { index ->

                            val business = state.myBusinessList[index]


                            MyBusinessCard(
                                business = business,
                                onClick = {
                                    onEvent(
                                        MyBusinessEvent.OnBusinessClicked(business.id)
                                    )
                                },
                                onEditClick = {
                                    onEvent(
                                        MyBusinessEvent.OnEditBusinessClicked(business.id)
                                    )
                                }
                            )
                        }
                    }
                }
            }

            // Loading
            if (state.isLoading) {
                LoadingDialogMolecule()
            }

            // Error
            AnimatedVisibility(state.isError) {
                ErrorDialogMolecule {
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
                id = 1,
                name = "Mecânica do Ricardo",
                category = "Mecânico",
                userId = 10,
                userName = "Ricardo",
                active = true,
                latitude = -23.561684,
                longitude = -46.625378,
                completeAddress = "Av. Paulista, 1500 - Bela Vista - São Paulo - SP",
                averageRating = 4.8,
                logoUrl = "https://randomuser.me/api/portraits/men/32.jpg",
                distanceKm = 2.5,
                description = "Especialista em motores e revisão completa.",
                telephone = "(11) 99999-9999",
                minimumPrice = 150.0,
                yearsOfExperience = 10,
                workingHours = "08:00 - 18:00",
                totalReviews = 124,
                totalCompletedOrders = 320,
                highlightReview = "Excelente atendimento!",
                highlightReviewAuthor = "João Silva",
                isFavorited = true
            ),
            MyBusinessEntity(
                id = 2,
                name = "Elétrica Silva",
                category = "Eletricista",
                userId = 11,
                userName = "Carlos Silva",
                active = false,
                latitude = -23.55052,
                longitude = -46.633308,
                completeAddress = "Rua Augusta, 500 - Consolação - São Paulo - SP",
                averageRating = 4.5,
                logoUrl = "https://randomuser.me/api/portraits/men/45.jpg",
                distanceKm = 5.2,
                description = "Instalações elétricas residenciais e comerciais.",
                telephone = "(11) 98888-7777",
                minimumPrice = 100.0,
                yearsOfExperience = 8,
                workingHours = "09:00 - 17:00",
                totalReviews = 89,
                totalCompletedOrders = 210,
                highlightReview = "Muito profissional!",
                highlightReviewAuthor = "Maria Souza",
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
