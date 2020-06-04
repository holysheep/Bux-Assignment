package com.assignment.core.domain.model.retreive

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.math.BigDecimal

// todo: check null possibilities

@JsonClass(generateAdapter = true)
internal data class TradingProduct(
    @Json(name = "symbol") val symbol: String,
    @Json(name = "securityId") val productId: String,
    @Json(name = "displayName") val title: String,
    @Json(name = "currentPrice") val currentPrice: Price,
    @Json(name = "closingPrice") val closingPrice: Price?
)

@JsonClass(generateAdapter = true)
internal data class Price(
    @Json(name = "currency") val currency: String,
    @Json(name = "decimals") val decimals: Int,
    @Json(name = "amount") val amount: BigDecimal
)