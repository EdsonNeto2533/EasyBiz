package com.mctable.easybiz.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mctable.easybiz.features.auth.presentation.ui.page.LoginPage
import com.mctable.easybiz.features.auth.presentation.view_model.LoginViewModel
import com.mctable.easybiz.features.search_business.presentation.ui.SearchBusinessPage
import com.mctable.easybiz.features.search_business.presentation.view_model.SearchBusinessViewModel
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navigator = getKoin()
        .get<Navigator>() as NavigatorImpl

    SideEffect {
        navigator.attach(navController)
    }

    NavHost(
        navController = navController,
        startDestination = Destination.Login.route
    ) {
        composable(Destination.Login.route) {
            val viewModel = koinViewModel<LoginViewModel>()
            LoginPage(state = viewModel.state, onEvent = { viewModel.onEvent(it) })
        }

        composable(Destination.SearchBusiness.route) {
            val viewModel = koinViewModel<SearchBusinessViewModel>()
            SearchBusinessPage(state = viewModel.state, onEvent = { viewModel.onEvent(it) })
        }
    }
}
