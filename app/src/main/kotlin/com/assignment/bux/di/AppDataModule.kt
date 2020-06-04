package com.assignment.bux.di

import com.assignment.core.data.di.dataModule
import com.assignment.core.data.di.networkModule

val appDataModule = listOf {
    networkModule
    dataModule
}
