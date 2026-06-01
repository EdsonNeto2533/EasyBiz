package com.mctable.easybiz.features.search_business.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mctable.easybiz.core.ds.components.atoms.TextInputAtom
import com.mctable.easybiz.core.ds.components.molecules.EmptyStateMolecule
import com.mctable.easybiz.core.ds.components.molecules.ErrorDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.LoadingDialogMolecule
import com.mctable.easybiz.core.ds.components.molecules.TopAppBarOrganism
import com.mctable.easybiz.core.ds.theme.Dimens
import com.mctable.easybiz.core.ds.utils.AppIcons
import com.mctable.easybiz.core.helpers.BindLocationTracker
import com.mctable.easybiz.core.helpers.rememberLocationTracker
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
    val listState = rememberLazyListState()

    BindLocationTracker(tracker)

    LaunchedEffect(Unit) {
        onEvent.invoke(SearchBusinessEvent.SetPermissionController(tracker))
        onEvent.invoke(SearchBusinessEvent.SearchBusiness)
    }

    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItemIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItemIndex >= state.businessList.size - 2 && state.businessList.isNotEmpty()
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) {
            onEvent(SearchBusinessEvent.LoadNextPage)
        }
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
                    state = listState,
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

                    if (state.isPaginationLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(Dimens.spacingMd),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            if (state.showLoading) {
                LoadingDialogMolecule()
            }

            AnimatedVisibility(state.showError) {
                ErrorDialogMolecule(
                    description = state.errorMessage ?: "Erro desconhecido"
                ) {
                    onEvent.invoke(SearchBusinessEvent.SearchBusiness)
                }
            }
        }
    }
}
