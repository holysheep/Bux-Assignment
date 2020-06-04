package com.assignment.core.domain.model

// todo: check possible exceptions and reformat accordingly to real errors
sealed class ApiError {
    object AuthError: ApiError()
    object SocketClosedError: ApiError()
    object UnexpectedError: ApiError()
    object NotValidTokenError: ApiError()
    object PermissionError: ApiError()

    data class NetworkError(val cause: Throwable): ApiError()
    data class SocketError(val cause: Throwable): ApiError()
}