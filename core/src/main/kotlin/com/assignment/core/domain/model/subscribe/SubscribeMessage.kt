package com.assignment.core.domain.model.subscribe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SubscribeMessage(
    @Json(name = "subscribeTo") val subscribeTo: List<TradingProductAction>,
    @Json(name = "unsubscribeFrom") val unsubscribeFrom: List<TradingProductAction>
)

internal class TradingProductAction(val stub: Unit) // todo: implement subscription sending