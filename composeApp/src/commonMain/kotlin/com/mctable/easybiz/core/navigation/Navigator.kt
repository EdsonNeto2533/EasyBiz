package com.mctable.easybiz.core.navigation


interface Navigator {

    fun navigate(
        destination: Destination,
        clearBackStack: Boolean = false
    ): Result<Unit>

    fun pop(): Result<Unit>

    fun popTo(
        destination: Destination,
        inclusive: Boolean = false
    ): Result<Unit>
}
