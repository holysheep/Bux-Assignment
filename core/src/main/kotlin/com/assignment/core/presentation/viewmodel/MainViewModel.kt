package com.assignment.core.presentation.viewmodel

import androidx.lifecycle.liveData
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.usecase.GetProductListUseCase
import com.assignment.core.presentation.base.BaseViewModel

internal class MainViewModel(
    private val productListUseCase: GetProductListUseCase
) : BaseViewModel() {

    val productList = liveData {
        when (val list = productListUseCase.execute()) {
            is NetworkResult.Success -> {
                emit(list.data)
            }
            is NetworkResult.Error -> {
                // todo: handle errors
            }
        }
    }

    fun onProductClicked(product: TradingProduct) {}
}