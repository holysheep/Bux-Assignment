package com.assignment.core.domain.repository

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct

internal interface TradingProductRepository {
    suspend fun getProductList() : NetworkResult<List<TradingProduct>>
    suspend fun getProductById(productId: String) : NetworkResult<TradingProduct>
}