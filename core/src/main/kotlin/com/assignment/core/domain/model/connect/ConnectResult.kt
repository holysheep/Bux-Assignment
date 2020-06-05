package com.assignment.core.domain.model.connect

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class ConnectResult(
    @Json(name = "t") val eventType: EventType?,
    val body: EventBody?
)

@JsonClass(generateAdapter = true)
internal data class EventBody(
    @Json(name = "developerMessage") val message: String?,
    val errorCode: String?
)

internal enum class EventType {
    @Json(name = "connect.connected")
    CONNECTED,
    @Json(name = "connect.failed")
    FAILED
}


