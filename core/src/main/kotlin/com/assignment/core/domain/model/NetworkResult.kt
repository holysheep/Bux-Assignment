package com.assignment.core.domain.model

internal sealed class NetworkResult<out T : Any?> {

    data class Success<out T : Any?>(val data: T?) : NetworkResult<T?>()
    data class Error(val error: ApiError) : NetworkResult<Nothing>()

    object Loading : NetworkResult<Nothing>()
}