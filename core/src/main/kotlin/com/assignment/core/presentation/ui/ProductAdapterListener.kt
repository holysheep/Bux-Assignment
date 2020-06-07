package com.assignment.core.presentation.ui

import com.assignment.core.domain.model.retreive.TradingProduct

internal interface ProductAdapterListener {
    fun onProductClicked(product: TradingProduct)
}