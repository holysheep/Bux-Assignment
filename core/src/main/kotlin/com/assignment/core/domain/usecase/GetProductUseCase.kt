package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository

internal class GetProductUseCase(private val repository: TradingProductRepository) :
    UseCase.RetrieveParamsUseCase<GetProductUseCase.Params, TradingProduct> {

    override suspend fun execute(params: Params): NetworkResult<TradingProduct> {
        return repository.getProductById(params.productId)
    }

    class Params(val productId: String) : UseCase.Params()
}