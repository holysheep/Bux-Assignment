package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository

internal class GetProductListUseCase(private val repository: TradingProductRepository) :
    UseCase.RetrieveUseCase<List<TradingProduct>> {

    override suspend fun execute(): NetworkResult<List<TradingProduct>> =
        repository.getProductList()
}