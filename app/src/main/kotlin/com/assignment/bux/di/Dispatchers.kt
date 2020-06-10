package com.assignment.bux.di

import com.assignment.core.data.utils.CoroutineDispatchers
import org.koin.dsl.module

val dispatchers = module {
    single { CoroutineDispatchers() }
}