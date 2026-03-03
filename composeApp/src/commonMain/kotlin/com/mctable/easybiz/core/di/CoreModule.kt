package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.config.AppEnv
import com.mctable.easybiz.core.config.AppEnvImpl
import com.mctable.easybiz.core.location.LocationProvider
import com.mctable.easybiz.core.location.LocationProviderImpl
import com.mctable.easybiz.core.navigation.Navigator
import com.mctable.easybiz.core.navigation.NavigatorImpl
import com.mctable.easybiz.core.networking.EasyBizNetworking
import com.mctable.easybiz.core.networking.EasyBizNetworkingImpl
import com.mctable.easybiz.core.networking.HttpClientFactory
import org.koin.dsl.module

val coreModule = module {
    single<EasyBizNetworking> {
        EasyBizNetworkingImpl(HttpClientFactory.build(get()))
    }
    single<Navigator> { NavigatorImpl() }
    single<AppEnv> { AppEnvImpl() }
    single<LocationProvider> {
        LocationProviderImpl(
            tracker = get()
        )
    }
}
