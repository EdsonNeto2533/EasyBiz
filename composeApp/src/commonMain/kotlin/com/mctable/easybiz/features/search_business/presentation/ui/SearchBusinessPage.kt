package com.mctable.easybiz.features.search_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.theme.EasyBizTheme
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.core.helpers.BindLocationTracker
import com.mctable.easybiz.core.helpers.rememberLocationTracker
import com.mctable.easybiz.features.search_business.domain.entity.BusinessEntity
import com.mctable.easybiz.features.search_business.presentation.event.SearchBusinessEvent
import com.mctable.easybiz.features.search_business.presentation.state.SearchBusinessState
import com.mctable.easybiz.features.search_business.presentation.ui.organisms.ProfessionalCardOrganism

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SearchBusinessPage(
    state: SearchBusinessState,
    onEvent: (SearchBusinessEvent) -> Unit,
    onOpenDrawer: () -> Unit = {}
) {

    val tracker = rememberLocationTracker()

    BindLocationTracker(tracker)

    LaunchedEffect(Unit) {
        onEvent.invoke(SearchBusinessEvent.SetPermissionController(tracker))
        onEvent.invoke(SearchBusinessEvent.SearchBusiness)
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "EasyBiz",
                showBackArrow = false,
                onMenuClick = onOpenDrawer
            )
        }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = Dimens.dividerThickness,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            TextInputAtom(
                "",
                placeHolder = state.inputPlaceholder,
                onChanged = { value ->
                    onEvent.invoke(SearchBusinessEvent.OnSearchValueChange(value))
                },
                keyboardActions = KeyboardActions(onSearch = {
                    onEvent.invoke(SearchBusinessEvent.SearchBusinessByName)
                }),
                imeAction = ImeAction.Search,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Dimens.screenPaddingHorizontal, vertical = Dimens.spacingSm),
                leadingIcon = AppIcons.search()
            )

            Text(
                state.title,
                modifier = Modifier.padding(
                    horizontal = Dimens.screenPaddingHorizontal,
                    vertical = Dimens.spacingSm
                ),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            if (state.businessList.isEmpty() && !state.showLoading) {
                EmptyStateMolecule(
                    icon = AppIcons.search(),
                    title = "Nenhum prestador encontrado",
                    description = "Tente ajustar sua busca ou localização"
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = Dimens.screenPaddingHorizontal),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
                ) {
                    items(state.businessList.size) { index ->
                        val business = state.businessList[index]
                        ProfessionalCardOrganism(
                            name = business.name,
                            profession = business.category,
                            rating = business.averageRating,
                            distance = "${business.distance}km de você",
                            imageUrl = business.logo,
                            onChatClick = {
                                onEvent.invoke(SearchBusinessEvent.OnBusinessClick(business.id))
                            },
                            onFavoriteClick = {
                                onEvent.invoke(SearchBusinessEvent.OnFavoriteClick(business.id))
                            }
                        )
                    }
                }
            }

            if (state.showLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.showError) {
                ErrorDialogMolecule {
                    onEvent.invoke(SearchBusinessEvent.SearchBusiness)
                }
            }
        }
    }
}


@Preview
@Composable
fun SearchBusinessPagePreview() {
    EasyBizTheme {
        SearchBusinessPage(
            state = SearchBusinessState(
                title = "Prestadores próximos",
                inputPlaceholder = "O que você precisa hoje",
                businessList = listOf(
                    BusinessEntity(
                        id = "",
                        name = "Oficina do Carlos",
                        category = "Mecânico",
                        userId = "",
                        userName = "Carlos Silva",
                        active = true,
                        latitude = -3.7319,
                        longitude = -38.5267,
                        completeAddress = "Av. Dom Luís, 1200 - Aldeota, Fortaleza - CE",
                        averageRating = 4.8,
                        logo = "https://picsum.photos/200/200?1",
                        distance = 2.0,
                        false
                    ),
                    BusinessEntity(
                        id = "",
                        name = "TechFix Assistência",
                        category = "Eletrônicos",
                        userId = "",
                        userName = "Mariana Costa",
                        active = true,
                        latitude = -3.7340,
                        longitude = -38.5215,
                        completeAddress = "Rua Barbosa de Freitas, 890 - Meireles, Fortaleza - CE",
                        averageRating = 4.6,
                        logo = "https://picsum.photos/200/200?2",
                        distance = 1.0,
                        true
                    ),
                    BusinessEntity(
                        id = "",
                        name = "Rei da Bicicleta",
                        category = "Bicicletaria",
                        userId = "",
                        userName = "João Pereira",
                        active = true,
                        latitude = -3.7285,
                        longitude = -38.5152,
                        completeAddress = "Rua Silva Paulet, 450 - Meireles, Fortaleza - CE",
                        averageRating = 4.9,
                        logo = "https://picsum.photos/200/200?3",
                        distance = 2.6,
                        false
                    )
                )
            ),
            onEvent = {}
        )
    }
}
