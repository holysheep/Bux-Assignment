package com.assignment.core.data.repository

import com.assignment.core.data.service.RestService
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository

internal class TradingProductRepositoryImpl(val restService: RestService) : TradingProductRepository {
    override suspend fun getProductList(): List<TradingProduct> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(productId: Int): TradingProduct {
        TODO("Not yet implemented")
    }
}