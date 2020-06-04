package com.assignment.core.domain.model.update

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

@JsonClass(generateAdapter = true)
data class RealtimeUpdateEvent(
    @Json(name = "t") val type: UpdateEventType,
    @Json(name = "body") val body: UpdateEventBody
)

@JsonClass(generateAdapter = true)
data class UpdateEventBody(
    @Json(name = "securityId") val productId: String?,
    @Json(name = "currentPrice") val updatedPrice: BigDecimal?
)

enum class UpdateEventType(val value: String) {
    @Json(name = "trading.quote")
    QUOTE_EVENT("trading.quote"),
    OTHER("")
}
