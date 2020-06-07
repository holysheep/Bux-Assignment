package com.assignment.core.domain.model.connect

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConnectResult(
    @Json(name = "t") val eventType: EventType?,
    val body: EventBody?
)

@JsonClass(generateAdapter = true)
internal data class ErrorMessage(
    val message: String?,
    val body: EventBody?
)

@JsonClass(generateAdapter = true)
internal data class EventBody(
    val developerMessage: String?,
    val errorCode: ErrorCodes
)

internal enum class EventType {
    @Json(name = "connect.connected")
    CONNECTED,
    @Json(name = "connect.failed")
    FAILED
}

internal enum class ErrorCodes {
    TRADING_002,
    AUTH_007,
    AUTH_014,
    AUTH_009,
    AUTH_008
}


