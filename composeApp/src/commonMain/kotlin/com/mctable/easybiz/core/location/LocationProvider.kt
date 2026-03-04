package com.mctable.easybiz.core.location

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocationProvider {
    fun observeLocation(): Flow<SimpleLocation>?
    suspend fun start()
    suspend fun stop()
    fun setTracker(tracker: LocationTracker)
}


class LocationProviderImpl : LocationProvider {

    private var tracker: LocationTracker? = null

    override fun observeLocation(): Flow<SimpleLocation>? {
        return tracker?.getLocationsFlow()?.map {
            SimpleLocation(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }

    override suspend fun start() {
        tracker?.startTracking()
    }

    override suspend fun stop() {
        tracker?.stopTracking()
    }

    override fun setTracker(tracker: LocationTracker) {
        this.tracker = tracker
    }
}