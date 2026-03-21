package com.mctable.easybiz.core.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mctable.easybiz.features.auth.presentation.ui.page.LoginPage
import com.mctable.easybiz.features.auth.presentation.view_model.LoginViewModel
import com.mctable.easybiz.features.business_details.presentation.ui.page.BusinessDetailsPage
import com.mctable.easybiz.features.business_details.presentation.view_model.BusinessDetailsViewModel
import com.mctable.easybiz.features.my_business.presentation.ui.MyBusinessPage
import com.mctable.easybiz.features.my_business.presentation.view_model.MyBusinessViewModel
import com.mctable.easybiz.features.my_favorites.presentation.ui.MyFavoritePage
import com.mctable.easybiz.features.my_favorites.presentation.view_model.MyFavoriteViewModel
import com.mctable.easybiz.features.my_orders.presentation.ui.MyOrderPage
import com.mctable.easybiz.features.my_orders.presentation.view_model.MyOrderViewModel
import com.mctable.easybiz.features.register_business.presentation.ui.RegisterBusinessPage
import com.mctable.easybiz.features.register_business.presentation.ui.UpdateBusinessProfilePage
import com.mctable.easybiz.features.register_business.presentation.view_model.RegisterBusinessViewModel
import com.mctable.easybiz.features.register_business.presentation.view_model.UpdateBusinessProfileViewModel
import com.mctable.easybiz.features.search_business.presentation.ui.SearchBusinessPage
import com.mctable.easybiz.features.search_business.presentation.view_model.SearchBusinessViewModel
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentDestination = Destination.fromRoute(currentRoute)

    LaunchedEffect(currentDestination) {
        if (!currentDestination.isLoggedArea) {
            drawerState.close()
        }
    }

    val navigator = getKoin()
        .get<Navigator>() as NavigatorImpl

    SideEffect {
        navigator.attach(navController)
    }

    NavDrawer(
        drawerState = drawerState,
        currentDestination = currentDestination,
        onDestinationClicked = { destination ->
            navigator.navigate(destination)
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = Destination.Login
        ) {
            composable<Destination.Login> {
                val viewModel = koinViewModel<LoginViewModel>()
                LoginPage(state = viewModel.state, onEvent = { viewModel.onEvent(it) })
            }

            composable<Destination.SearchBusiness> {
                val viewModel = koinViewModel<SearchBusinessViewModel>()
                SearchBusinessPage(
                    state = viewModel.state,
                    onEvent = { viewModel.onEvent(it) },
                    onOpenDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            }

            composable<Destination.BusinessDetails> { backStackEntry ->
                val route: Destination.BusinessDetails = backStackEntry.toRoute()

                val viewModel = koinViewModel<BusinessDetailsViewModel>()

                BusinessDetailsPage(
                    state = viewModel.state,
                    onEvent = { viewModel.onEvent(it) },
                    id = route.id
                )
            }

            composable<Destination.RegisterBusiness> {
                val viewModel = koinViewModel<RegisterBusinessViewModel>()
                RegisterBusinessPage(
                    state = viewModel.state,
                    onEvent = { viewModel.onEvent(it) },
                )
            }

            composable<Destination.UpdateBusiness> {
                val route = it.toRoute<Destination.UpdateBusiness>()
                val viewModel = koinViewModel<UpdateBusinessProfileViewModel>()

                UpdateBusinessProfilePage(
                    state = viewModel.state,
                    onEvent = { event -> viewModel.onEvent(event) },
                    id = route.id
                )

            }

            composable<Destination.MyBusiness> {
                val viewModel = koinViewModel<MyBusinessViewModel>()

                MyBusinessPage(
                    state = viewModel.state,
                    onEvent = { event -> viewModel.onEvent(event) },
                )

            }

            composable<Destination.MyFavorites> {
                val viewModel = koinViewModel<MyFavoriteViewModel>()

                MyFavoritePage(
                    state = viewModel.state,
                    onEvent = { event -> viewModel.onEvent(event) },
                )
            }

            composable<Destination.MyOrders> {
                val viewModel = koinViewModel<MyOrderViewModel>()

                MyOrderPage(
                    state = viewModel.state,
                    onEvent = { event -> viewModel.onEvent(event) },
                )
            }
        }
    }
}
