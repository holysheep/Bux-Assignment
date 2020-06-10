package com.assignment.core.domain.error

import com.assignment.core.data.Constants
import kotlinx.coroutines.delay
import java.io.IOException

internal object RetryConnection {
    /**
     * This function is used to retry connection attempt in case
     * when its impossible to catch errors within reactive operators.
     */
    suspend fun <T> retryConnection(
        times: Int = Int.MAX_VALUE,
        initialDelay: Long = 100, // 0.1 second
        maxDelay: Long = Constants.ONE_SECOND_DELAY,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelay
        repeat(times - 1) {
            try {
                return block()
            } catch (e: IOException) {
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelay)
        }
        return block() // last attempt
    }
}