package com.mctable.easybiz.features.business_details.presentation.ui.page

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mctable.easybiz.core.ds.components.atoms.AvatarAtom
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.core.ds.components.atoms.PillAtom
import com.mctable.easybiz.core.ds.components.atoms.PillType
import com.mctable.easybiz.core.ds.components.atoms.RatingAtom
import com.mctable.easybiz.core.ds.components.atoms.SectionHeaderAtom
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.MediaViewerDialog
import com.mctable.easybiz.core.ds.components.molecules.ProfilePhotoAction
import com.mctable.easybiz.core.ds.components.molecules.ProfilePhotoViewerDialog
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.features.business_details.domain.entity.BusinessDetailsEntity
import com.mctable.easybiz.features.business_details.presentation.event.BusinessDetailsEvent
import com.mctable.easybiz.features.business_details.presentation.state.BusinessDetailsState
import com.mctable.easybiz.features.business_details.presentation.ui.molecules.BadgeCardMolecule
import com.mctable.easybiz.features.reviews.presentation.ui.components.ReviewCard


@Composable
fun BusinessDetailsPage(
    state: BusinessDetailsState,
    onEvent: (BusinessDetailsEvent) -> Unit,
    id: String
) {
    val scrollState = rememberScrollState()
    val business = state.businessDetails

    var viewerVisible by remember { mutableStateOf(false) }
    var viewerIndex by remember { mutableStateOf(0) }
    val mediaUrls = state.mediaList.filter { !it.isVideo }.map { it.url }
    var logoViewerVisible by remember { mutableStateOf(false) }

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
            if (state.businessDetails?.isMine == false)
                Column {
                    ButtonAtom(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = Dimens.screenPaddingHorizontal,
                            ),
                        text = state.startChatLabel,
                        onClick = {
                            onEvent(BusinessDetailsEvent.CreateOrder)
                        }
                    )
                    Box(modifier = Modifier.height(12.dp))
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

            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { logoViewerVisible = true }
            ) {
                AvatarAtom(
                    imageUrl = business?.logoUrl,
                    contentDescription = business?.name,
                    size = Dimens.avatarSizeXl,
                    showOnlineIndicator = false
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
                PillAtom(pillType = PillType.Success, text = state.availableLabel)
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

            if (!business?.telephone.isNullOrBlank()) {
                Spacer(Modifier.height(Dimens.spacingXxl))
                SectionHeaderAtom(
                    title = "Telefone / WhatsApp",
                    subtitle = business?.telephone ?: ""
                )
            }

            if (!business?.workingHours.isNullOrBlank()) {
                Spacer(Modifier.height(Dimens.spacingXxl))
                SectionHeaderAtom(
                    title = "Horário de atendimento",
                    subtitle = business?.workingHours ?: ""
                )
            }

            Spacer(Modifier.height(Dimens.spacingXxl))

            SectionHeaderAtom(
                title = state.descriptionLabel,
                subtitle = business?.description ?: ""
            )

            if (state.mediaList.isNotEmpty()) {
                HorizontalDivider(
                    thickness = Dimens.dividerThickness,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Spacer(Modifier.height(Dimens.spacingXxl))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Portfólio",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "${state.mediaList.size} foto(s)",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(Dimens.spacingMd))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacingSm)
                ) {
                    items(state.mediaList) { media ->
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .clickable {
                                    val photoIndex = mediaUrls.indexOf(media.url).takeIf { it >= 0 } ?: 0
                                    viewerIndex = photoIndex
                                    viewerVisible = true
                                }
                        ) {
                            AsyncImage(
                                model = media.url,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                            if (media.isVideo) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(androidx.compose.ui.graphics.Color.Black.copy(alpha = 0.5f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = AppIcons.send(),
                                        contentDescription = "Vídeo",
                                        tint = androidx.compose.ui.graphics.Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(Dimens.spacingXxl))
            }

            if (state.reviews.isNotEmpty()) {
                HorizontalDivider(
                    thickness = Dimens.dividerThickness,
                    color = MaterialTheme.colorScheme.outlineVariant
                )

                Spacer(Modifier.height(Dimens.spacingXxl))

                SectionHeaderAtom(
                    title = "Avaliações",
                    subtitle = "${state.reviews.size} avaliação(ões)"
                )

                Spacer(Modifier.height(Dimens.spacingMd))

                state.reviews.forEach { review ->
                    ReviewCard(review = review)
                    Spacer(Modifier.height(Dimens.spacingMd))
                }
            }

            Spacer(Modifier.height(Dimens.spacing4xl))

            if (state.showLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.showError) {
                ErrorDialogMolecule(
                    description = state.errorMessage ?: "Erro desconhecido",
                ) {
                    onEvent.invoke(BusinessDetailsEvent.GetBusinessDetails(id))
                }
            }
        }
    }

    if (viewerVisible && mediaUrls.isNotEmpty()) {
        MediaViewerDialog(
            urls = mediaUrls,
            initialIndex = viewerIndex,
            onDismiss = { viewerVisible = false }
        )
    }

    if (logoViewerVisible) {
        ProfilePhotoViewerDialog(
            photoUrl = business?.logoUrl,
            onDismiss = { logoViewerVisible = false },
            actions = if (state.isOwner && business != null) listOf(
                ProfilePhotoAction(
                    icon = {
                        Icon(
                            painter = AppIcons.accountCircle(),
                            contentDescription = null,
                            tint = androidx.compose.ui.graphics.Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    label = "Editar perfil",
                    onClick = {
                        logoViewerVisible = false
                        onEvent(BusinessDetailsEvent.EditBusiness(business.id))
                    }
                )
            ) else emptyList()
        )
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
            telephone = null,
            workingHours = "Seg-Sex 8h-18h",
            minimalValue = 100.0,
            yearsOfExperience = 5,
            totalRatings = 10,
            false
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