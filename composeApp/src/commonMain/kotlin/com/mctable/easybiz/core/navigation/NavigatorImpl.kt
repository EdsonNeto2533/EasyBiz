package com.mctable.easybiz.core.navigation


import androidx.navigation.NavHostController

class NavigatorImpl() : Navigator {

    private var navController: NavHostController? = null

    fun attach(navController: NavHostController) {
        this.navController = navController
    }

    override fun navigate(
        destination: Destination,
        clearBackStack: Boolean
    ): Result<Unit> = runCatching {
        val controller = navController

        controller?.navigate(destination.route) {
            if (clearBackStack) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    override fun pop(): Result<Unit> = runCatching {
        val controller = navController

        controller?.popBackStack()
    }

    override fun popTo(
        destination: Destination,
        inclusive: Boolean
    ): Result<Unit> = runCatching {
        val controller = navController

        controller?.popBackStack(destination.route, inclusive)
    }
}
