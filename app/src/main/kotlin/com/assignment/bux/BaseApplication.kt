package com.assignment.bux

import android.app.Application
import com.assignment.bux.di.appDataModule
import com.assignment.bux.di.appDomainModule
import com.assignment.bux.di.appViewModelModule
import com.assignment.bux.di.dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val appModules = appDataModule + appDomainModule + appViewModelModule + dispatchers
        startKoin {
            androidContext(this@BaseApplication)
            if (BuildConfig.DEBUG) {
                androidLogger(Level.INFO)
            }
            modules(appModules)
        }
    }
}