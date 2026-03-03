package com.mctable.easybiz.features.search_business.presentation.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.features.search_business.domain.usecase.SearchBusinessUseCase
import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.launch

class SearchBusinessViewModel(
    private val searchBusinessUseCase: SearchBusinessUseCase,
    private val navigator: Navigator,
    private val locationTracker: LocationTracker
): ViewModel() {


    fun teste(){
        viewModelScope.launch {

        }
    }

}