package com.mctable.easybiz.core.di

import com.mctable.easybiz.core.notification.di.notificationModule
import com.mctable.easybiz.features.auth.di.authModule
import com.mctable.easybiz.features.business_details.di.businessDetailsModule
import com.mctable.easybiz.features.business_media.di.businessMediaModule
import com.mctable.easybiz.features.my_business.di.myBusinessModule
import com.mctable.easybiz.features.my_favorites.di.myFavoriteModule
import com.mctable.easybiz.features.my_orders.di.myOrderModule
import com.mctable.easybiz.features.order_chat.di.orderChatModule
import com.mctable.easybiz.features.register_business.di.registerBusinessModule
import com.mctable.easybiz.features.search_business.di.searchBusinessModule
import com.mctable.easybiz.features.reviews.di.reviewModule
import com.mctable.easybiz.features.user_data.di.userDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.includes

fun initProjectKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        includes(config)
        modules(
            coreModule,
            notificationModule,
            authModule,
            platformModule(),
            searchBusinessModule,
            businessDetailsModule,
            registerBusinessModule,
            businessMediaModule,
            myBusinessModule,
            myFavoriteModule,
            myOrderModule,
            orderChatModule,
            userDataModule,
            reviewModule
        )
    }
}