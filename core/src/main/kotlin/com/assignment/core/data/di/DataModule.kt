package com.assignment.core.data.di

import com.assignment.core.data.provider.WebsocketLifecycleRegistryProvider
import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.data.repository.TradingProductRepositoryImpl
import com.assignment.core.data.utils.lazy
import com.assignment.core.domain.provider.LifecycleRegistryProvider
import com.assignment.core.domain.provider.WebSocketProvider
import com.assignment.core.domain.repository.TradingProductRepository
import org.koin.dsl.module

val dataModule = module {
    single<TradingProductRepository> {
        TradingProductRepositoryImpl(
            restService = get(),
            moshi = get()
        )
    }
}

val webSocketModule = module {
    single<WebSocketProvider> {
        ScarletSocketProvider(
            socketService = lazy(),
            dispatchers = get()
        )
    }

    single<LifecycleRegistryProvider> {
        WebsocketLifecycleRegistryProvider(lifecycleRegistry = get())
    }
}