package com.assignment.core.domain.usecase

import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.update.RealtimeUpdateEvent

internal class ObserveProductUpdateUseCase(provider: ScarletSocketProvider) :
    UseCase.RetrieveUseCase<RealtimeUpdateEvent> {
    override suspend fun execute(): NetworkResult<RealtimeUpdateEvent?> {
        TODO("Not yet implemented")
    }
}