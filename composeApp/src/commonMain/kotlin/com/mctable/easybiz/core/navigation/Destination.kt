package com.mctable.easybiz.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.mctable.easybiz.core.ds.utils.AppIcons
import kotlinx.serialization.Serializable

@Serializable
sealed interface Destination {

    val route: String
    val isLoggedArea: Boolean
    val title: String? get() = null

    @Serializable
    data object Login : Destination {
        override val route = "/login"
        override val isLoggedArea = false
    }

    @Serializable
    data object SearchBusiness : Destination {
        override val route = "/search-business"
        override val isLoggedArea = true
        override val title = "Buscar prestadores"
    }

    @Serializable
    data class BusinessDetails(val id: Int) : Destination {
        override val route = "$BASE_ROUTE/$id"
        override val isLoggedArea = true

        companion object {
            const val BASE_ROUTE = "/business-details"
            const val ROUTE_PATTERN = "$BASE_ROUTE/{id}"
        }
    }

    companion object {
        fun fromRoute(route: String?): Destination {
            return when {
                route == Login.route -> Login
                route == SearchBusiness.route -> SearchBusiness
                route?.startsWith(BusinessDetails.BASE_ROUTE) == true -> {
                    val id = route.substringAfterLast("/").toIntOrNull() ?: 0
                    BusinessDetails(id)
                }
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
