package com.assignment.core.data

internal object Constants {
    const val WS_PATH = "/subscriptions/me"

    // Auth headers
    const val HEADER_AUTH = "Authorization"
    const val HEADER_ACCEPT = "Accept"
    const val HEADER_ACCEPT_LANGUAGE = "Accept-Language"

    const val TOKEN_ACCESS = "Bearer %s"
    const val MIME_TYPE = "application/json"
    const val ACCEPT_LANGUAGE = "nl-NL,en;q=0.8"

    const val BACKOFF_DURATION_BASE = 5000L
    const val BACKOFF_DURATION_MAX = 5000L
}