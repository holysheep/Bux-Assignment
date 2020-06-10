package com.assignment.core.presentation.di

import com.assignment.core.presentation.viewmodel.MainViewModel
import com.assignment.core.presentation.viewmodel.ProductRealtimeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        MainViewModel(
            productListUseCase = get(),
            resources = androidContext().resources
        )
    }
    viewModel {
        ProductRealtimeViewModel(
            getProductUseCase = get(),
            observeProductUpdateUseCase = get(),
            observeWebsocketUseCase = get(),
            resources = androidContext().resources
        )
    }
}