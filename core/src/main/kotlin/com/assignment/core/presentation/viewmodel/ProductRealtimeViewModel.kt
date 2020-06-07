package com.assignment.core.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.*
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.usecase.GetProductUseCase
import com.assignment.core.domain.usecase.ObserveProductUpdateUseCase
import com.assignment.core.domain.usecase.ObserveWebsocketUseCase
import com.assignment.core.presentation.base.BaseViewModel
import com.assignment.core.presentation.utils.networkErrorString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class ProductRealtimeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val observeProductUpdateUseCase: ObserveProductUpdateUseCase,
    observeWebsocketUseCase: ObserveWebsocketUseCase,
    private val resources: Resources
) : BaseViewModel() {

    private val _tradingProduct = MutableLiveData<TradingProduct>()
    val tradingProduct: LiveData<TradingProduct> = _tradingProduct

    val webSocketState = observeWebsocketUseCase
        .execute()
        .asLiveData()

    fun showProduct(productId: String) {
        viewModelScope.launch {
            when (val result = getProductUseCase.execute(GetProductUseCase.Params(productId))) {
                is NetworkResult.Loading -> {
                }
                is NetworkResult.Success -> {
                    _tradingProduct.postValue(result.data) // check if not empty
                    listenForProductUpdate(productId)
                }
                is NetworkResult.Error -> {
                    errorValue.value = resources.getFormattedString(result.error)
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
                        is NetworkResult.Loading -> {
                            // show progress bar
                        }
                        is NetworkResult.Success -> {
                            // update price + format
                        }
                        is NetworkResult.Error -> {
                            errorValue.value = resources.getFormattedString(result.error)
                        }
                    }
                }
        }
    }

    fun updatePrice() {}

    fun formatPrice() {}
}