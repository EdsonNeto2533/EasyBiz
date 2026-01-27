package com.mctable.easybiz.core.application

import android.app.Application
import com.mctable.easybiz.core.di.initProjectKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class EasyBizApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initProjectKoin {
            androidContext(this@EasyBizApplication)
            androidLogger()
        }
    }
}