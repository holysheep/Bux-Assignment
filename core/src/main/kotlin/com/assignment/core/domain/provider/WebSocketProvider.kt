package com.assignment.core.domain.provider

import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.flow.Flow

internal interface WebSocketProvider {
    fun connect()
    fun observeWebSocketConnection() : Flow<WebSocket.Event>
    fun subscribeToProduct(toProductIdentifier: String,
                           fromProductIdentifier: String?): Flow<RealtimeUpdateEvent>
}