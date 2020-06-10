package com.assignment.core.infrastructure

import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.ShutdownReason
import com.tinder.scarlet.lifecycle.LifecycleRegistry
import kotlinx.coroutines.flow.collect

class ConnectivityOnLifecycleProvider(
    private val connectivityState: DeviceConnectivityState,
    private val lifecycleRegistry: LifecycleRegistry = LifecycleRegistry()
) : Lifecycle by lifecycleRegistry {

    suspend fun <T> observeConnectivity(action: (Boolean) -> T) {
        connectivityState
            .changes()
            .collect { action(it) }
    }

    fun switchWebSocketLifecycleState(isConnected: Boolean) {
        lifecycleRegistry.onNext(toLifecycleState(isConnected))
    }

    private fun toLifecycleState(isConnected: Boolean): Lifecycle.State = if (isConnected) {
        Lifecycle.State.Started
    } else {
        Lifecycle.State.Stopped.WithReason(ShutdownReason(1000,""))
    }
}