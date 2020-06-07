package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.assignment.core.domain.provider.WebSocketProvider
import kotlinx.coroutines.flow.Flow

internal class ObserveProductUpdateUseCase(private val provider: WebSocketProvider) :
    UseCase.RetrieveParamsFlowUseCase<ObserveProductUpdateUseCase.Params, RealtimeUpdateEvent> {

    override fun execute(params: Params): Flow<NetworkResult<RealtimeUpdateEvent>> {
        return provider.subscribeToProduct(
            params.toProductId,
            params.fromProductId)
    }

    class Params(
        val toProductId: String,
        val fromProductId: String? = null
    ) : UseCase.Params()
}