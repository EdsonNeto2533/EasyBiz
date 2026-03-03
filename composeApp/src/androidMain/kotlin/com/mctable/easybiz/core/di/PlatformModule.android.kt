package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.local_storage.AndroidEasyBizStorage
import com.mctable.easybiz.core.local_storage.EasyBizStorage
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<EasyBizStorage> {
        AndroidEasyBizStorage(get()) // Koin will provide the Android Context
    }
    single {
        LocationTracker(
            permissionsController = PermissionsController(get())
        )
    }
}