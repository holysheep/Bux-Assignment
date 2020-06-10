package com.assignment.core.domain.model.connect

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConnectResult(
    @Json(name = "t") val eventType: EventType?,
    @Json(name = "body") val body: EventBody?
)

@JsonClass(generateAdapter = true)
internal data class EventBody(
    @Json(name = "developerMessage") val developerMessage: String?,
    @Json(name = "errorCode") val errorCode: String?
)

internal enum class EventType {
    @Json(name = "connect.connected")
    CONNECTED,

    @Json(name = "connect.failed")
    FAILED
}

@JsonClass(generateAdapter = true)
internal data class ErrorMessage(
    val message: String?,
    val developerMessage: String?,
    val errorCode: ErrorCodes?
)

internal enum class ErrorCodes {
    TRADING_002,
    AUTH_007,
    AUTH_014,
    AUTH_009,
    AUTH_008
}


