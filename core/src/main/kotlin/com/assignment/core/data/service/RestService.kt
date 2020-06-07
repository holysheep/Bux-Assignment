package com.assignment.core.data.service

import com.assignment.core.domain.model.retreive.TradingProduct
import retrofit2.http.GET
import retrofit2.http.Path

internal interface RestService {

    @GET("core/21/products/")
    suspend fun fetchProductList(): List<TradingProduct>

    @GET("core/21/products/{identifier}")
    suspend fun fetchProductById(@Path("identifier") productId: String): TradingProduct
}