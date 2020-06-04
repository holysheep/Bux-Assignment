package com.assignment.core.data.provider

import com.assignment.core.data.service.SocketService
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.assignment.core.domain.provider.WebSocketProvider
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

internal class ScarletSocketProvider(val socketService: SocketService) : WebSocketProvider {
    override fun connect() {
        TODO("Not yet implemented")
    }

    override fun observeWebSocketConnection(): Flow<WebSocket.Event> {
        TODO("Not yet implemented")
    }

    override fun subscribeToProduct(
        toProductIdentifier: String,
        fromProductIdentifier: String?
    ): Flow<RealtimeUpdateEvent> {
        TODO("Not yet implemented")
    }
}