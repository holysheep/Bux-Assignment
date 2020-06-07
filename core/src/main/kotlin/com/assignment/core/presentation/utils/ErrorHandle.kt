package com.assignment.core.presentation.utils

import android.content.res.Resources
import androidx.annotation.StringRes
import com.assignment.core.R
import com.assignment.core.domain.error.Failure

fun Failure.networkErrorString(resources: Resources): String {
    return when (this) {
        is Failure.HttpError -> resources.localizeErrorMessage(formatHttpError())
        is Failure.ServerError -> resources.localizeErrorMessage(R.string.server_error)
        is Failure.NetworkError -> resources.localizeErrorMessage(R.string.network_error)
        is Failure.AuthError -> resources.localizeErrorMessage(R.string.auth_error)
        is Failure.NotValidTokenError -> resources.localizeErrorMessage(R.string.auth_token_error)
        is Failure.SocketConnectClosed,
        is Failure.SocketConnectFailed -> resources.localizeErrorMessage(R.string.network_socket_error)
        is Failure.NoRouteToHostError -> resources.localizeErrorMessage(R.string.network_error)
        is Failure.SubscriptionFailed -> resources.localizeErrorMessage(R.string.subscription_error)
        is Failure.UnexpectedError -> resources.getString(R.string.unknown_error)
    }
}

fun Resources.localizeErrorMessage(@StringRes errorStringRes: Int): String =
    getString(errorStringRes)

fun Failure.HttpError.formatHttpError(): Int {
    return when (statusCode) {
        Failure.StatusCode.BAD_REQUEST -> R.string.server_error__message_400
        Failure.StatusCode.UNAUTHENTICATED -> R.string.server_error__message_401
        Failure.StatusCode.FORBIDDEN -> R.string.server_error__message_403
        Failure.StatusCode.NOT_FOUND -> R.string.server_error__message_404
        Failure.StatusCode.INTERNAL_ERROR -> R.string.server_error__message_500
        Failure.StatusCode.SERVICE_UNAVAILABLE -> R.string.server_error__message_503
        Failure.StatusCode.GATEWAY_TIMEOUT -> R.string.server_error__message_504
        else -> R.string.server_error__message_unknown
    }
}