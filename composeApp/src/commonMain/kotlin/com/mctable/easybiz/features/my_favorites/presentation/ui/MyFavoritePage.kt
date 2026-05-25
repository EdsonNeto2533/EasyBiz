package com.mctable.easybiz.features.my_favorites.presentation.ui

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
import com.mctable.easybiz.features.my_favorites.domain.entity.MyFavoriteEntity
import com.mctable.easybiz.features.my_favorites.presentation.event.MyFavoriteEvent
import com.mctable.easybiz.features.my_favorites.presentation.state.MyFavoriteState
import com.mctable.easybiz.features.my_favorites.presentation.ui.components.MyFavoriteCard

@Composable
fun MyFavoritePage(
    state: MyFavoriteState,
    onEvent: (MyFavoriteEvent) -> Unit
) {

    LaunchedEffect(Unit) {
        onEvent.invoke(MyFavoriteEvent.GetMyFavorites)
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBarOrganism(
                title = "Meus favoritos",
                showBackArrow = true,
                onBackClick = { onEvent(MyFavoriteEvent.OnBackPressed) }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                state.favoriteList.isEmpty() && !state.isLoading -> {
                    EmptyStateMolecule(
                        icon = AppIcons.star(),
                        title = "Nenhum favorito ainda",
                        description = "Adicione prestadores aos seus favoritos para acessá-los rapidamente"
                    )
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(Dimens.screenPaddingHorizontal),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacingMd)
                    ) {
                        items(state.favoriteList.size) { index ->
                            val favorite = state.favoriteList[index]
                            MyFavoriteCard(
                                favorite = favorite,
                                onClick = {
                                    onEvent(MyFavoriteEvent.OnBusinessClicked(favorite.businessId))
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
                    onEvent.invoke(MyFavoriteEvent.GetMyFavorites)
                }
            }
        }
    }
}

@Preview
@Composable
fun MyFavoritePagePreview() {
    val state = MyFavoriteState(
        favoriteList = listOf(
            MyFavoriteEntity(
                id = "",
                businessId = "",
                businessName = "Barbearia do João",
                category = "BARBEARIA",
                averageRating = 4.5,
                logoUrl = "https://randomuser.me/api/portraits/men/32.jpg",
                createdAt = "2024-03-21T14:58:36.096Z"
            )
        )
    )

    EasyBizTheme {
        MyFavoritePage(
            state = state,
            onEvent = {}
        )
    }
}
