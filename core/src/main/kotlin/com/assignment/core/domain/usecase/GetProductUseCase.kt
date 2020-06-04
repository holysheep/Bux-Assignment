package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository

internal class GetProductUseCase(repository: TradingProductRepository) :
    UseCase.RetrieveUseCase<TradingProduct> {
    override suspend fun execute(): NetworkResult<TradingProduct?> {
        TODO("Not yet implemented")
    }
}