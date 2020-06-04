package com.assignment.core.domain.repository

import com.assignment.core.domain.model.retreive.TradingProduct

internal interface TradingProductRepository {
    suspend fun getProductList() : List<TradingProduct>
    suspend fun getProductById(productId: Int) : TradingProduct
}