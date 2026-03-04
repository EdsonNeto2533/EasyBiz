package com.mctable.easybiz.core.helpers

import androidx.compose.runtime.Composable
import dev.icerock.moko.geo.LocationTracker
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun rememberLocationTracker(): LocationTracker {

    val permissionsController = rememberLocationPermissionController()

    return koinInject(
        parameters = { parametersOf(permissionsController) }
    )
}

@Composable
expect fun BindLocationTracker(tracker: LocationTracker)