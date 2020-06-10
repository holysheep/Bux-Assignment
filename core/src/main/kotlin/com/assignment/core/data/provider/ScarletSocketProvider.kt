package com.assignment.core.data.provider

import com.assignment.core.data.Constants
import com.assignment.core.data.service.SocketService
import com.assignment.core.data.utils.CoroutineDispatchers
import com.assignment.core.data.utils.Lazy
import com.assignment.core.domain.error.Failure
import com.assignment.core.domain.error.RetryConnection.retryConnection
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.connect.ConnectResult
import com.assignment.core.domain.model.connect.EventType
import com.assignment.core.domain.model.subscribe.SubscribeMessage
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.assignment.core.domain.model.update.UpdateEventType
import com.assignment.core.domain.provider.WebSocketProvider
import com.tinder.scarlet.WebSocket
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import okhttp3.internal.format

internal class ScarletSocketProvider(
    private var socketService: Lazy<SocketService>,
    private val dispatchers: CoroutineDispatchers
) : WebSocketProvider {

    override fun observeWebSocketConnection(): Flow<NetworkResult<WebSocket.Event>> {
        return callbackFlow {
            socketService.get()
                .observeWebSocketConnection()
                .flowOn(dispatchers.default)
                .filterIsInstance<WebSocket.Event.OnConnectionFailed>()
                .buffer()
                .collect {
                    offer(NetworkResult.Error(Failure.SocketConnectFailed(it.throwable.cause)))
                    close()
                }
            awaitClose()
        }
    }

    private fun connect(): Flow<ConnectResult> =
        socketService.get().connectToBuxFeed()

    override fun subscribeToProduct(
        toProductIdentifier: String,
        fromProductIdentifier: String?
    ): Flow<NetworkResult<RealtimeUpdateEvent>> {
        return callbackFlow {
            connect()
                .takeWhile { it.eventType != null }
                .collect { connectResult ->
                    when (connectResult.eventType) {
                        EventType.CONNECTED -> {
                            changeSubscriptionProduct(toProductIdentifier).let { subscribed ->
                                if (subscribed) {
                                    socketService.get()
                                        .observeRealtimeEvent()
                                        .catch { e -> offer(NetworkResult.Error(Failure.ServerError(e))) }
                                        .flowOn(dispatchers.default)
                                        .filter { it.type == UpdateEventType.QUOTE_EVENT }
                                        .buffer()
                                        .collectLatest { offer(NetworkResult.Success(it)) }
                                } else {
                                    offer(NetworkResult.Error(Failure.SubscriptionFailed))
                                }
                            }
                        }
                        EventType.FAILED -> {
                            offer(NetworkResult.Error(Failure.ServerError()))
                            retryConnection {
                                subscribeToProduct(toProductIdentifier, fromProductIdentifier)
                            }
                        }
                    }
                }
            awaitClose()
        }
    }

    private fun changeSubscriptionProduct(
        toProductId: String,
        fromProductId: String? = null
    ): Boolean {
        return socketService.get()
            .changeSubscriptionProduct(
                SubscribeMessage(
                    subscribeTo = listOf(format(Constants.IDENTIFIER_BASE, toProductId)),
                    unsubscribeFrom = fromProductId?.let { listOf(format(Constants.IDENTIFIER_BASE, fromProductId)) }
                        ?: emptyList()
                )
            )
    }
}