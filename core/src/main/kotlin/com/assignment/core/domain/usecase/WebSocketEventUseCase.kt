package com.assignment.core.domain.usecase

import com.assignment.core.data.provider.ScarletSocketProvider
import com.assignment.core.domain.model.NetworkResult
import com.tinder.scarlet.WebSocket

internal class WebSocketEventUseCase(val provider: ScarletSocketProvider) :
    UseCase.RetrieveUseCase<WebSocket.Event> {
    override suspend fun execute(): NetworkResult<WebSocket.Event?> {
        TODO("Not yet implemented")
    }
}