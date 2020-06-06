package com.assignment.core.data.repository

import com.assignment.core.data.service.RestService
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository

internal class TradingProductRepositoryImpl(val restService: RestService) : TradingProductRepository {
    override suspend fun getProductList(): NetworkResult<List<TradingProduct>> {
        return restService.fetchProductList()
    }

    override suspend fun getProductById(productId: String): NetworkResult<TradingProduct> {
        return restService.fetchProductById(productId)
    }
}