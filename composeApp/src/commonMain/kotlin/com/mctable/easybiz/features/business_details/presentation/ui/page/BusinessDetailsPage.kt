package com.mctable.easybiz.features.business_details.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.PillAtom
import com.mctable.easybiz.core.ds.components.atoms.PillType
import com.mctable.easybiz.core.ds.components.atoms.RatingAtom
import com.mctable.easybiz.core.ds.components.atoms.SectionHeaderAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
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
    id: String
) {

    val scrollState = rememberScrollState()
    val business = state.businessDetails

    LaunchedEffect(Unit) {
        onEvent.invoke(BusinessDetailsEvent.GetBusinessDetails(id))
    }

    Scaffold(

        containerColor = MaterialTheme.colorScheme.background,

        topBar = {
            TopAppBarOrganism(
                title = state.pageTitle,
                showBackArrow = true,
                onBackClick = { onEvent(BusinessDetailsEvent.OnBackClick) }
            )
        },

        bottomBar = {
            Surface(
                tonalElevation = 2.dp,
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                ButtonAtom(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Dimens.screenPaddingHorizontal,
                            vertical = Dimens.spacingMd
                        ),
                    text = state.startChatLabel,
                    onClick = {
                        onEvent(BusinessDetailsEvent.CreateOrder)
                    }
                )
            }
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(scrollState)
                .padding(horizontal = Dimens.screenPaddingHorizontal),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(Dimens.spacingXxl))

            Box(contentAlignment = Alignment.Center) {
                AvatarAtom(
                    imageUrl = business?.logoUrl,
                    contentDescription = business?.name,
                    size = Dimens.avatarSizeXl,
                    showOnlineIndicator = business?.active == true
                )
            }

            Spacer(Modifier.height(Dimens.spacingLg))

            Text(
                text = business?.name ?: "",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(Dimens.spacingXs))

            Text(
                text = business?.category ?: "",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(Dimens.spacingSm))

            if (business?.active == true) {
                PillAtom(
                    pillType = PillType.Success,
                    text = state.availableLabel
                )
                Spacer(Modifier.height(Dimens.spacingSm))
            }

            RatingAtom(
                rating = business?.averageRating ?: 0.0,
                reviewCount = business?.totalRatings ?: 0
            )

            Spacer(Modifier.height(Dimens.spacingXxl))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
            ) {
                BadgeCardMolecule(
                    painter = AppIcons.verified(),
                    value = "${business?.yearsOfExperience ?: 0} anos",
                    label = "Experiência",
                    modifier = Modifier.weight(1f)
                )
                BadgeCardMolecule(
                    painter = AppIcons.creditCard(),
                    value = "R$ ${business?.minimalValue ?: 0}",
                    label = "Preço médio",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(Dimens.spacingXxl))

            HorizontalDivider(
                thickness = Dimens.dividerThickness,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Spacer(Modifier.height(Dimens.spacingXxl))

            SectionHeaderAtom(
                title = state.addressTitle,
                subtitle = business?.completeAddress ?: ""
            )

            Spacer(Modifier.height(Dimens.spacingXxl))

            SectionHeaderAtom(
                title = state.descriptionLabel,
                subtitle = business?.description ?: ""
            )

            Spacer(Modifier.height(Dimens.spacing4xl))

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
            description = "Somos um profissional de mecânica com plus de 4 anos no mercado fazendo coisas e mais coisas",
            minimalValue = 100.0,
            yearsOfExperience = 5,
            totalRatings = 10
        )
    )

    EasyBizTheme {

        BusinessDetailsPage(
            state = state,
            onEvent = {},
            "1"
        )
    }
}
