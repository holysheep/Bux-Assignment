package com.assignment.core.presentation.utils

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

object PriceHelper {
    fun String.getCurrency(): Currency? = Currency.getInstance(this)
    fun String.getCurrencySymbol(): String? = getCurrency()?.symbol

    fun localeAwareFormatting(
        currency: String,
        price: BigDecimal,
        fractionDigitsNumber: Int?
    ): String? {
        currency.getCurrency()?.let {
            val decimalFormat = DecimalFormat.getInstance().apply {
                minimumFractionDigits = fractionDigitsNumber ?: it.defaultFractionDigits
                maximumFractionDigits = fractionDigitsNumber ?: it.defaultFractionDigits
            }
            return decimalFormat.format(price)
        }
        return null
    }

    fun priceChange(
        currentAmount: BigDecimal,
        closingAmount: BigDecimal
    ): BigDecimal {
        return currentAmount.subtract(closingAmount)
    }

    fun priceChangePercentage(
        currentAmount: BigDecimal,
        closingAmount: BigDecimal
    ): BigDecimal {

        // (P2 - P1) / |P1| * 100%
        val change = priceChange(currentAmount, closingAmount)
            .divide(closingAmount, 4, RoundingMode.HALF_EVEN)
            .multiply(ONE_HUNDRED)
        return rounded(change)
    }

    fun formatPercentage(percentage: BigDecimal): String =
        DecimalFormat(PERCENTAGE_PATTERN).format(percentage) + "%"

    fun formatPriceChange(price: BigDecimal): String {
        return if (price.signum() > 0) { // a way bit hardcore return of plus
            "+$price"
        } else {
            price.toString()
        }
    }

    private fun rounded(number: BigDecimal): BigDecimal =
        number.setScale(PERCENT_SCALE, ROUNDING_MODE)

    private val ONE_HUNDRED = BigDecimal("100")
    private val ROUNDING_MODE = RoundingMode.HALF_EVEN
    private const val PERCENTAGE_PATTERN = "+#,##0.00;-#" // Includes minus/plus for percentage
    private const val PERCENT_SCALE = 2 // Number of decimals to retain
}