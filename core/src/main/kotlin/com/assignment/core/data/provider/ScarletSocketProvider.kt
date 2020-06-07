package com.assignment.core.data.provider

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.assignment.core.data.Constants
import com.assignment.core.data.service.SocketService
import com.assignment.core.domain.error.Failure
import com.assignment.core.domain.model.NetworkResult
import com.assignment.core.domain.model.connect.ConnectResult
import com.assignment.core.domain.model.connect.EventType
import com.assignment.core.domain.model.subscribe.SubscribeMessage
import com.assignment.core.domain.model.update.RealtimeUpdateEvent
import com.assignment.core.domain.model.update.UpdateEventType
import com.assignment.core.domain.provider.WebSocketProvider
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.io.IOException

internal class ScarletSocketProvider(
    private val context: Application,
    lifecycle: Lifecycle,
    private val scarlet: Scarlet.Builder
) : WebSocketProvider, DefaultLifecycleObserver {

    private var scarletInstance: Scarlet? = null
    private val socketService: SocketService
        get() {
            return scarletInstance?.create() ?: run {
                buildScarletInstance().create<SocketService>()
            }
        }

    init {
        lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        scarletInstance = buildScarletInstance(owner)
    }

    private fun buildScarletInstance(owner: LifecycleOwner? = null): Scarlet {
        return owner?.let {
            // tie scarlet connection to caller lifecycle
            scarlet
                .lifecycle(AndroidLifecycle.ofLifecycleOwnerForeground(context, owner))
                .build()
        } ?: run {
            scarlet.build()
        }
    }

    override fun observeWebSocketConnection(): Flow<NetworkResult<WebSocket.Event>> {
        return callbackFlow {
            socketService
                .observeWebSocketConnection()
                .collectLatest {
                    when (it) {
                        is WebSocket.Event.OnConnectionClosed -> {
                            offer(NetworkResult.Error(Failure.SocketConnectClosed(it.shutdownReason.reason)))
                            close()
                        }
                        is WebSocket.Event.OnConnectionFailed -> {
                            offer(NetworkResult.Error(Failure.SocketConnectFailed(it.throwable.cause)))
                            close(it.throwable)
                        }
                    }
                }
            awaitClose()
        }
    }

    private fun connect(): Flow<ConnectResult> =
        socketService.connectToBuxFeed()

    override fun subscribeToProduct(
        toProductIdentifier: String,
        fromProductIdentifier: String?
    ): Flow<NetworkResult<RealtimeUpdateEvent>> {
        return flow {
            emit(NetworkResult.Loading)
            connect()
                .catch { e -> emit(NetworkResult.Error(Failure.ServerError(e))) }
                .filterNot { it.eventType == null } // ignore any other event
                .collectLatest { connectResult ->
                    when (connectResult.eventType) {
                        EventType.CONNECTED -> {
                            changeSubscriptionProduct(toProductIdentifier).let { subscribed ->
                                if (subscribed) {
                                    socketService
                                        .observeRealtimeEvent()
//                                        .buffer() // todo: how often this will trigger?
                                        .filter { it.type == UpdateEventType.QUOTE_EVENT }
                                        .collectLatest {
                                            emit(NetworkResult.Success(it))
                                        }
                                } else {
                                    emit(NetworkResult.Error(Failure.SubscriptionFailed))
                                }
                            }
                        }
                        EventType.FAILED -> {
                            emit(NetworkResult.Error(Failure.ServerError()))
                            retryConnection(times = Constants.RETRY_ATTEMPTS_AMOUNT) {
                                subscribeToProduct(toProductIdentifier, fromProductIdentifier)
                            }
                        }
                    }
                }
        }
    }

    private fun changeSubscriptionProduct(productId: String): Boolean {
        val fullIdentifier = "trading.product.$productId"
        // todo: add unsubscribe list
        return socketService.changeSubscriptionProduct(
            SubscribeMessage(
                subscribeTo = listOf(fullIdentifier),
                unsubscribeFrom = emptyList()
            )
        )
    }

    private suspend fun <T> retryConnection(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 100, // 0.1 second
        maxDelay: Long = 1000,    // 1 second
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) { }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }
}