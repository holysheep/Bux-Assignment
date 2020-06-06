package com.assignment.core.domain.di

import com.assignment.core.domain.usecase.GetProductListUseCase
import com.assignment.core.domain.usecase.GetProductUseCase
import com.assignment.core.domain.usecase.ObserveProductUpdateUseCase
import com.assignment.core.domain.usecase.ObserveWebsocketUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { GetProductListUseCase(repository = get()) }
    factory { GetProductUseCase(repository = get()) }
    factory { ObserveProductUpdateUseCase(provider = get()) }
    factory { ObserveWebsocketUseCase(provider = get()) }
}