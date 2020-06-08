package com.assignment.bux.di

import com.assignment.core.data.di.dataModule
import com.assignment.core.data.di.networkModule
import com.assignment.core.data.di.webSocketModule
import com.assignment.core.infrastructure.DeviceConnectivityState
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val appDataModule = listOf(
    networkModule,
    dataModule,
    webSocketModule,

    module {
        single { DeviceConnectivityState(context = androidApplication()) }
    }
)
