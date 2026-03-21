package com.mctable.easybiz.features.my_favorites.presentation.view_model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Destination
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.my_favorites.domain.usecase.GetMyFavoritesUseCase
import com.mctable.easybiz.features.my_favorites.presentation.event.MyFavoriteEvent
import com.mctable.easybiz.features.my_favorites.presentation.state.MyFavoriteState
import kotlinx.coroutines.launch

class MyFavoriteViewModel(
    private val getMyFavoritesUseCase: GetMyFavoritesUseCase,
    private val navigator: Navigator
) : ViewModel() {

    var state by mutableStateOf(MyFavoriteState())
        private set

    init {
        onEvent(MyFavoriteEvent.GetMyFavorites)
    }

    fun onEvent(event: MyFavoriteEvent) {
        when (event) {
            MyFavoriteEvent.GetMyFavorites -> loadMyFavorites()
            MyFavoriteEvent.OnBackPressed -> navigator.pop()
            is MyFavoriteEvent.OnBusinessClicked -> {
                navigator.navigate(Destination.BusinessDetails(event.businessId))
            }
        }
    }

    private fun loadMyFavorites() {
        state = state.copy(isLoading = true, isError = false)
        viewModelScope.launch {
            getMyFavoritesUseCase.execute().fold(
                onSuccess = { list ->
                    state = state.copy(
                        favoriteList = list,
                        isLoading = false
                    )
                },
                onFailure = {
                    state = state.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            )
        }
    }
}
