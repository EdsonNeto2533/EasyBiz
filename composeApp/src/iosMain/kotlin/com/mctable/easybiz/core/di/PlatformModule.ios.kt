package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.local_storage.EasyBizStorage
import com.mctable.easybiz.core.local_storage.IosEasyBizStorage
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.ios.PermissionsController
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<EasyBizStorage> {
        IosEasyBizStorage()
    }

    single {
        LocationTracker(
            permissionsController = PermissionsController()
        )
    }
}