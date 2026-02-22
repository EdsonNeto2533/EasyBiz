package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.local_storage.AndroidEasyBizStorage
import com.mctable.easybiz.core.local_storage.EasyBizStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<EasyBizStorage> {
        AndroidEasyBizStorage(get()) // Koin will provide the Android Context
    }
}