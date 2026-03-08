package com.mctable.easybiz.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.mctable.easybiz.core.ds.utils.AppIcons

sealed interface Destination {

    val route: String
    val isLoggedArea: Boolean
    val title: String? get() = null

    data object Login : Destination {
        override val route = "/login"
        override val isLoggedArea = false
    }

    data object SearchBusiness : Destination {
        override val route = "/search-business"
        override val isLoggedArea = true
        override val title = "Buscar prestadores"
    }

    companion object {
        fun fromRoute(route: String?): Destination {
            return when (route) {
                Login.route -> Login
                SearchBusiness.route -> SearchBusiness
                else -> Login
            }
        }

        @Composable
        fun iconFromRoute(route: String?): Painter {
            return when (route) {
                SearchBusiness.route -> AppIcons.contactEmail()
                else -> AppIcons.arrowBack()
            }
        }

        val drawerDestinations = listOf(
            SearchBusiness
        )
    }
}
