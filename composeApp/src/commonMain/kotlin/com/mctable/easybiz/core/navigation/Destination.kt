package com.mctable.easybiz.core.navigation

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

        val drawerDestinations = listOf(
            SearchBusiness
        )
    }
}
