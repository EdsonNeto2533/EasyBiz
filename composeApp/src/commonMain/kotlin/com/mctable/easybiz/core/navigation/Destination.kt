package com.mctable.easybiz.core.navigation

sealed interface Destination {

    val route: String

    data object Login : Destination {
        override val route = "/login"
    }


}
