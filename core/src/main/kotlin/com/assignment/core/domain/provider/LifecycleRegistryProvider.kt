package com.assignment.core.domain.provider

import androidx.lifecycle.LifecycleOwner

interface LifecycleRegistryProvider {
    fun setWebSocketLifecycleObserver(lifecycleOwner: LifecycleOwner)
}