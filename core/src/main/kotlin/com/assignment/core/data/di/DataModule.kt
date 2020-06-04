package com.assignment.core.data.di

import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.data.repository.TradingProductRepositoryImpl
import com.assignment.core.data.service.SocketService
import com.assignment.core.domain.provider.WebSocketProvider
import com.assignment.core.domain.repository.TradingProductRepository
import com.tinder.scarlet.Scarlet
import org.koin.dsl.module

val dataModule = module {

    single<TradingProductRepository> { TradingProductRepositoryImpl(restService = get()) }
}

val webSocketModule = module {

    single { get<Scarlet>().create(SocketService::class.java) }
    single<WebSocketProvider> { ScarletSocketProvider(socketService = get()) }
}