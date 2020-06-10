package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.retreive.TradingProduct
import com.assignment.core.domain.repository.TradingProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class GetProductListUseCase(private val repository: TradingProductRepository) :
    UseCase.RetrieveFlowUseCase<List<TradingProduct>> {
    override fun execute(): Flow<NetworkResult<List<TradingProduct>?>> {
        return flow { emit(repository.getProductList()) }
            .flowOn(Dispatchers.Default)
    }
}