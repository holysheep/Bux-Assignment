package com.assignment.core.data.provider

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.assignment.core.domain.provider.LifecycleRegistryProvider
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.ShutdownReason
import com.tinder.scarlet.lifecycle.LifecycleRegistry

internal class WebsocketLifecycleRegistryProvider(private val lifecycleRegistry: LifecycleRegistry) :
    LifecycleRegistryProvider,
    DefaultLifecycleObserver,
    Lifecycle by lifecycleRegistry {

    override fun setWebSocketLifecycleObserver(lifecycleOwner: LifecycleOwner,
                                               doOnRestart: () -> Unit) {
        lifecycleOwner.lifecycle.addObserver(LifecycleObserver(doOnRestart))
    }

    inner class LifecycleObserver(private val doOnRestart: () -> Unit) : DefaultLifecycleObserver{
        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            lifecycleRegistry.onNext(Lifecycle.State.Started)
            doOnRestart()
        }

        override fun onPause(owner: LifecycleOwner) {
            super.onPause(owner)
            lifecycleRegistry.onNext(
                Lifecycle.State.Stopped.WithReason(ShutdownReason(1000, "Paused"))
            )
        }

        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            lifecycleRegistry.onNext(Lifecycle.State.Stopped.AndAborted)
        }
    }
}