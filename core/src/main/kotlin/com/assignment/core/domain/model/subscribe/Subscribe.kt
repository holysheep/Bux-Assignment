package com.assignment.core.domain.model.subscribe

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class SubscribeMessage(
    @Json(name = "subscribeTo")
    val subscribeTo: List<String>?,
    @Json(name = "unsubscribeFrom")
    val unsubscribeFrom: List<String>?
)