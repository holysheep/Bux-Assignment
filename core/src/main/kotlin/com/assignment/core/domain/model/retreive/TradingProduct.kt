package com.assignment.core.domain.model.retreive

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

// todo: check null possibilities

@JsonClass(generateAdapter = true)
internal data class TradingProduct(
    val symbol: String,
    @Json(name = "securityId") val productId: String,
    @Json(name = "displayName") val title: String,
    val currentPrice: Price,
    val closingPrice: Price?
)

@JsonClass(generateAdapter = true)
internal data class Price(
    val currency: String,
    val decimals: Int,
    val amount: BigDecimal
)