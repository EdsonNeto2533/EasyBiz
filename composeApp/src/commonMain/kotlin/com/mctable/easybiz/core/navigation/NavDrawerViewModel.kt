package com.mctable.easybiz.core.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.features.auth.domain.usecase.LogoutUseCase
import kotlinx.coroutines.launch

class NavDrawerViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val navigator: Navigator
) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute().onSuccess {
                navigator.popUpTo(Destination.Login)
            }
        }
    }
}
