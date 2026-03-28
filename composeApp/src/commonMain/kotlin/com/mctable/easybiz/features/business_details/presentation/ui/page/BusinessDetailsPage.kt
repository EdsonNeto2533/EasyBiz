package com.mctable.easybiz.features.business_details.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_details.presentation.event.BusinessDetailsEvent
import com.mctable.easybiz.features.business_details.presentation.state.BusinessDetailsState
import com.mctable.easybiz.features.business_details.presentation.ui.molecules.BadgeCardMolecule


@Composable
fun BusinessDetailsPage(
    state: BusinessDetailsState,
    onEvent: (BusinessDetailsEvent) -> Unit,
    id: Int
) {

    val scrollState = rememberScrollState()
    val business = state.businessDetails

    LaunchedEffect(Unit) {
        onEvent.invoke(BusinessDetailsEvent.GetBusinessDetails(id))
    }

    Scaffold(

        topBar = {
            TopAppBarOrganism(
                title = state.pageTitle,
                showBackArrow = true,
                onBackClick = { onEvent(BusinessDetailsEvent.OnBackClick) }
            )
        },

        bottomBar = {
            ButtonAtom(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                text = state.startChatLabel,
                onClick = {
                    onEvent(BusinessDetailsEvent.StartChat)
                }
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(24.dp))

            Box(
                contentAlignment = Alignment.Center
            ) {

                business?.logoUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Icon(
                        AppIcons.contactEmail(),
                        null,
                        modifier = Modifier
                            .size(120.dp),
                    )
                }


                if (business?.active == true) {

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {

                        Text(
                            text = state.availableLabel,
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = business?.name ?: "",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = business?.category ?: "",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = AppIcons.star(),
                    contentDescription = null,
                    tint = Color.Yellow.copy(alpha = 0.7f)
                )

                Spacer(Modifier.width(6.dp))

                Text(
                    text = "${business?.averageRating ?: 0.0} (${business?.totalRatings ?: 0} avaliações)",
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(24.dp))

            Row(modifier = Modifier.wrapContentHeight()) {
                BadgeCardMolecule(
                    painter = AppIcons.verified(),
                    value = "${business?.yearsOfExperience ?: 0} anos",
                    label = "Experiência",
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(24.dp))
                BadgeCardMolecule(
                    painter = AppIcons.creditCard(),
                    value = "R$ ${business?.minimalValue ?: 0}",
                    label = "Preço médio",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = state.addressTitle,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = business?.completeAddress ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = state.descriptionLabel,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = business?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(32.dp))

            if (state.showLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.showError) {
                ErrorDialogMolecule {
                    onEvent.invoke(BusinessDetailsEvent.GetBusinessDetails(id))
                }
            }
        }
    }
}

@Composable
@Preview
fun BusinessDetailsPagePreview() {

    val state = BusinessDetailsState(
        pageTitle = "Perfil do Prestador",
        availableLabel = "Disponível",
        addressTitle = "Endereço",
        startChatLabel = "Iniciar chat",
        descriptionLabel = "Sobre",
        businessDetails = BusinessDetailsEntity(
            id = "",
            name = "Ricardo Alencar",
            category = "Mecânico Especialista",
            userId = "",
            userName = "Ricardo",
            active = true,
            latitude = 0.0,
            longitude = 0.0,
            completeAddress = "Av. Paulista, 1500 - Bela Vista - São Paulo - SP",
            averageRating = 4.9,
            logoUrl = null,
            description = "Somos um profissional de mecânica com mais de 4 anos no mercado fazendo coisas e mais coisas",
            minimalValue = 100.0,
            yearsOfExperience = 5,
            totalRatings = 10
        )
    )

    EasyBizTheme {

        BusinessDetailsPage(
            state = state,
            onEvent = {},
            1
        )
    }
}