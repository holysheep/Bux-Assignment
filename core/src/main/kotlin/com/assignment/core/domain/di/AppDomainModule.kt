package com.assignment.core.domain.di

import com.assignment.core.domain.usecase.GetProductUseCase
import com.assignment.core.domain.usecase.ObserveProductUpdateUseCase
import com.assignment.core.domain.usecase.SendSubscribeUseCase
import com.assignment.core.domain.usecase.WebSocketEventUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProductUseCase(repository = get()) }
    factory { ObserveProductUpdateUseCase(provider = get()) }
    factory { SendSubscribeUseCase(provider = get()) }
    factory { WebSocketEventUseCase(provider = get()) }
}