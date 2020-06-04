package com.assignment.core.data.service

import com.assignment.core.domain.model.connect.ConnectResult
import com.assignment.core.domain.model.subscribe.SubscribeMessage
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import kotlinx.coroutines.flow.Flow

internal interface SocketService {
    @Receive
    fun connectToBuxFeed(): Flow<ConnectResult>

    @Receive
    fun observeWebSocketConnection(): Flow<WebSocket.Event>

    @Send
    fun changeSubscriptionProduct(subscribe: SubscribeMessage)

    @Receive
    fun observeRealtimeEvent(): Flow<RealtimeUpdateEvent>
}