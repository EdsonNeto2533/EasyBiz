package com.mctable.easybiz.features.my_favorites.presentation.event

sealed class MyFavoriteEvent {
    data object GetMyFavorites : MyFavoriteEvent()
    data object OnBackPressed : MyFavoriteEvent()
    data class OnBusinessClicked(val businessId: String) : MyFavoriteEvent()
    data class OnRemoveFavorite(val businessId: String) : MyFavoriteEvent()
}
