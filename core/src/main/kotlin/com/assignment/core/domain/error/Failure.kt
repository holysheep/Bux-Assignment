package com.assignment.core.domain.error

import retrofit2.HttpException

sealed class Failure {
    // Common network, server etc. errors
    object AuthError : Failure()
    object NotValidTokenError : Failure()

    class HttpError(val error: HttpException) : Failure(), HttpResponse {
        override val statusCode: Int get() = error.code()
        override val statusMessage: String? get() = error.message()
        override val serverErrorBody: String? get() = error.response()?.errorBody()?.string() // bux
    }

    data class ServerError(val cause: Throwable? = null) : Failure()
    data class NetworkError(val cause: Throwable?) : Failure()
    data class UnexpectedError(val cause: Throwable? = null) : Failure()

    // Socket errors
    data class SocketConnectFailed(val cause: Throwable?) : Failure()
    data class SocketConnectClosed(val reason: String?) : Failure()
    object NoRouteToHostError : Failure()
    object SubscriptionFailed : Failure()

    object StatusCode {
        //    Client errors
        const val BAD_REQUEST = 400
        const val UNAUTHENTICATED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404

        //    Server errors
        const val INTERNAL_ERROR = 500
        const val SERVICE_UNAVAILABLE = 503
        const val GATEWAY_TIMEOUT = 504
    }

    interface HttpResponse {
        val statusCode: Int
        val statusMessage: String?
        val serverErrorBody: String?
    }
}