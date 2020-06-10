package com.assignment.core.data.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class CoroutineDispatchers (
    /**
     * The [CoroutineDispatcher] that is designed for execute long operations on a shared pool of threads.
     * Default value is [Dispatchers.Default]
     */
    val default: CoroutineDispatcher = Dispatchers.Default
) {
    companion object {
        fun test() = CoroutineDispatchers(
            default = Dispatchers.Unconfined
        )
    }
}

