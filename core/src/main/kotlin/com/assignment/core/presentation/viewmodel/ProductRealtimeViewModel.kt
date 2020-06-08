package com.assignment.core.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.usecase.GetProductUseCase
import com.assignment.core.domain.usecase.ObserveProductUpdateUseCase
import com.assignment.core.domain.usecase.ObserveWebsocketUseCase
import com.assignment.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ProductRealtimeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val observeProductUpdateUseCase: ObserveProductUpdateUseCase,
    val observeWebsocketUseCase: ObserveWebsocketUseCase,
    private val resources: Resources
) : BaseViewModel() {

    private val _tradingProduct = MutableLiveData<TradingProduct?>()
    val tradingProduct: LiveData<TradingProduct?> = _tradingProduct

    fun showProduct(productId: String) {
        viewModelScope.launch {
            uiStateValue.value = UIState.LOADING
            when (val result = getProductUseCase.execute(GetProductUseCase.Params(productId))) {
                is NetworkResult.Success -> {
                    result.data?.let { product ->
                        _tradingProduct.postValue(product)
                        uiStateValue.value = UIState.READY

                        observeWebSocketState()
//                        listenForProductUpdate(productId)
                    } ?: run { uiStateValue.value = UIState.EMPTY }
                }
                is NetworkResult.Error -> {
                    errorStateValue.value = resources.getFormattedString(result.error)
                }
            }
        }
    }

    private fun listenForProductUpdate(productId: String) {
        viewModelScope.launch { // io?
            observeProductUpdateUseCase
                .execute(ObserveProductUpdateUseCase.Params(toProductId = productId))
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            // update price + format
                        }
                        is NetworkResult.Error -> {
                            errorStateValue.value = resources.getFormattedString(result.error)
                        }
                    }
                }
        }
    }

    private fun observeWebSocketState() {
        viewModelScope.launch {
            observeWebsocketUseCase.execute()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            errorStateValue.value = resources.getFormattedString(result.error)
                        }
                    }
                }
        }
    }

    fun updatePrice() {}

    fun formatPrice() {}
}