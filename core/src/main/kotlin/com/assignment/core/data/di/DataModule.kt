package com.assignment.core.data.di

import androidx.lifecycle.ProcessLifecycleOwner
import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.data.repository.TradingProductRepositoryImpl
import com.assignment.core.domain.provider.WebSocketProvider
import com.assignment.core.domain.repository.TradingProductRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
    single<TradingProductRepository> { TradingProductRepositoryImpl(restService = get()) }
}

val webSocketModule = module {
    single<WebSocketProvider> {
        ScarletSocketProvider(
            context = androidApplication(),
            lifecycle = ProcessLifecycleOwner.get().lifecycle,
            scarlet = get()
        )
    }
}