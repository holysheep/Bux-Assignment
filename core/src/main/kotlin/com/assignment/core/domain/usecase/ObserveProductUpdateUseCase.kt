package com.assignment.core.domain.usecase

import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import kotlinx.coroutines.flow.Flow

internal class ObserveProductUpdateUseCase(private val provider: ScarletSocketProvider) :
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