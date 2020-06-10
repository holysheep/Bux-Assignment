package com.assignment.core.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.assignment.core.domain.error.Failure
import com.assignment.core.domain.error.RetryConnection.retryConnection
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.usecase.GetProductUseCase
import com.assignment.core.domain.usecase.ObserveProductUpdateUseCase
import com.assignment.core.domain.usecase.ObserveWebsocketUseCase
import com.assignment.core.presentation.base.BaseViewModel
import com.assignment.core.presentation.utils.PriceHelper.formatPercentage
import com.assignment.core.presentation.utils.PriceHelper.formatPriceChange
import com.assignment.core.presentation.utils.PriceHelper.getCurrencySymbol
import com.assignment.core.presentation.utils.PriceHelper.localeAwareFormatting
import com.assignment.core.presentation.utils.PriceHelper.priceChange
import com.assignment.core.presentation.utils.PriceHelper.priceChangePercentage
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import java.math.BigDecimal

internal class ProductRealtimeViewModel(
    private val getProductUseCase: GetProductUseCase,
    private val observeProductUpdateUseCase: ObserveProductUpdateUseCase,
    private val observeWebsocketUseCase: ObserveWebsocketUseCase,
    private val resources: Resources
) : BaseViewModel() {

    private lateinit var tradingProduct: TradingProduct
    val productTitle = MutableLiveData<String?>()
    val productPrice = MutableLiveData<String?>()
    val difference = MutableLiveData<String?>()
    val percentageDifference = MutableLiveData<String?>()

    fun showProduct(productId: String) {
        viewModelScope.launch {
            uiState.value = UIState.LOADING
            when (val result = getProductUseCase.execute(GetProductUseCase.Params(productId))) {
                is NetworkResult.Success -> {
                    result.data?.let { product ->
                        tradingProduct = product

                        uiState.value = UIState.READY
                        productTitle.postValue(product.title)

                        // Price amount
                        val formattedPrice = formattedPrice(
                            currentProduct = product,
                            amount = product.currentPrice.amount
                        )
                        productPrice.postValue(formattedPrice)

                        // Price change
                        difference.postValue(
                            formatPriceChange(priceChange(
                                currentAmount = product.currentPrice.amount,
                                closingAmount = product.closingPrice.amount
                            ))
                        )
                        percentageDifference.postValue(
                            formatPercentage(priceChangePercentage(
                                currentAmount = product.currentPrice.amount,
                                closingAmount = product.closingPrice.amount
                            ))
                        )

                        observeWebSocketState()
                        listenForProductUpdate(productId)
                    } ?: run { uiState.value = UIState.EMPTY }
                }
                is NetworkResult.Error -> {
                    if (result.error is Failure.NetworkError) {
                        retryConnection { showProduct(productId) }
                    }
                    errorStateValue.value = resources.getFormattedString(result.error)
                }
            }
        }
    }

    fun listenForProductUpdate(productId: String) {
        viewModelScope.launch {
            observeProductUpdateUseCase
                .execute(ObserveProductUpdateUseCase.Params(toProductId = productId))
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val update = result.data?.body
                            update?.updatedPrice?.let { updatedPrice ->
                                productPrice.value = formattedPrice(
                                    currentProduct = tradingProduct,
                                    amount = updatedPrice
                                )

                                difference.value = formatPriceChange(priceChange(
                                    currentAmount = updatedPrice,
                                    closingAmount = tradingProduct.closingPrice.amount
                                ))

                                percentageDifference.value = formatPercentage(priceChangePercentage(
                                    currentAmount = updatedPrice,
                                    closingAmount = tradingProduct.closingPrice.amount
                                ))
                            }
                        }
                        is NetworkResult.Error -> {
                            if (result.error is Failure.SocketConnectFailed) {
                                retryConnection { listenForProductUpdate(productId) }
                            }
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

    private fun formattedPrice(
        currentProduct: TradingProduct,
        amount: BigDecimal
    ): String? {
        val decimals = currentProduct.currentPrice.decimals
        val currency = currentProduct.currentPrice.currency
        val currencySymbol = currency.getCurrencySymbol()
        val localeAwareFormatting = localeAwareFormatting(
            currency = currency,
            price = amount,
            fractionDigitsNumber = decimals
        )
        return "$currencySymbol $localeAwareFormatting"
    }
}