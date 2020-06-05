package com.assignment.bux.di

import com.assignment.core.data.di.dataModule
import com.assignment.core.data.di.networkModule
import com.assignment.core.data.di.webSocketModule

val appDataModule = listOf(
    networkModule,
    dataModule,
    webSocketModule
)
