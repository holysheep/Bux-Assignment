package com.assignment.core.domain.usecase

import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.provider.WebSocketProvider
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

internal class ObserveWebsocketUseCase(private val provider: WebSocketProvider) :
    UseCase.RetrieveFlowUseCase<WebSocket.Event> {
    override fun execute(): Flow<NetworkResult<WebSocket.Event?>> {
        return provider.observeWebSocketConnection()
    }
}