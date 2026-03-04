package com.mctable.easybiz.core.helpers

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import dev.icerock.moko.geo.LocationTracker

@Composable
actual fun BindLocationTracker(tracker: LocationTracker) {
    val activity = LocalActivity.current as ComponentActivity

    DisposableEffect(activity, tracker) {
        tracker.bind(activity)
        onDispose { }
    }
}