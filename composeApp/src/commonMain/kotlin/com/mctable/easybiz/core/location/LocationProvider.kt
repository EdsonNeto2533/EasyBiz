package com.mctable.easybiz.core.location

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface LocationProvider {
    fun observeLocation(): Flow<SimpleLocation>
    suspend fun start()
    suspend fun stop()
}


class LocationProviderImpl(
    private val tracker: LocationTracker
) : LocationProvider {

    override fun observeLocation(): Flow<SimpleLocation> {
        return tracker.getLocationsFlow().map {
            SimpleLocation(
                latitude = it.latitude,
                longitude = it.longitude
            )
        }
    }

    override suspend fun start() {
        tracker.startTracking()
    }

    override suspend fun stop() {
        tracker.stopTracking()
    }
}