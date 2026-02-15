package com.mctable.easybiz.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mctable.easybiz.core.ds.components.atoms.ButtonAtom
import com.mctable.easybiz.features.auth.presentation.ui.page.LoginPage
import com.mctable.easybiz.features.auth.presentation.view_model.LoginViewModel
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
    }
}
