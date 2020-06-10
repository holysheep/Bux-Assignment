package com.assignment.core.domain.model

import com.assignment.core.domain.error.Failure

internal sealed class NetworkResult<out T : Any?> {

    data class Success<out T : Any?>(val data: T?) : NetworkResult<T>()
    data class Error(val error: Failure) : NetworkResult<Nothing>()
}