package com.mctable.easybiz.core.di

import com.mctable.easybiz.features.auth.di.authModule
import com.mctable.easybiz.features.search_business.di.searchBusinessModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initProjectKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(coreModule, authModule, platformModule(), searchBusinessModule)
    }
}