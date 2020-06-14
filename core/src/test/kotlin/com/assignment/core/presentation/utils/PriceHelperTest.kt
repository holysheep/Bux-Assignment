package com.assignment.core.presentation.utils

import com.assignment.core.presentation.utils.PriceHelper.formatPercentage
import com.assignment.core.presentation.utils.PriceHelper.formatPriceChange
import com.assignment.core.presentation.utils.PriceHelper.getCurrencySymbol
import com.assignment.core.presentation.utils.PriceHelper.localeAwareFormatting
import com.assignment.core.presentation.utils.PriceHelper.priceChange
import com.assignment.core.presentation.utils.PriceHelper.priceChangePercentage
import com.assignment.core.presentation.utils.PriceHelper.rounded
import com.google.common.truth.Truth
import org.junit.Test
import java.math.BigDecimal

class PriceHelperTest {

    @Test
    fun getCurrencySymbolReturnsAppropriateSymbolSign() {
        val symbol = "EUR".getCurrencySymbol()

        Truth.assertThat(symbol).isEqualTo("€") // check locale? ISO 4217
    }

    @Test
    fun localeAwareFormattingFormatReturnsTheRightString() {
        val currency = "EUR"
        val fractionDigitsNumber = 1
        val price = BigDecimal("12005.5")

        val localeAwareFormatting = localeAwareFormatting(currency, price, fractionDigitsNumber)

        Truth.assertThat(localeAwareFormatting).isEqualTo("12,005.5")
    }

    @Test
    fun priceChangeSubtractReturnTheRightNumber() {
        val bd1 = BigDecimal("12922.5")
        val bd2 = BigDecimal("12848.8")

        val result = priceChange(currentAmount = bd2, closingAmount = bd1)

        Truth.assertThat(result).isEqualTo(BigDecimal("-73.7"))
    }

    @Test
    fun priceChangePercentageCountRight() {
        val bd1 = BigDecimal("12922.5")
        val bd2 = BigDecimal("12848.8")

        val result = priceChangePercentage(currentAmount = bd2, closingAmount = bd1)

        Truth.assertThat(result).isEqualTo(BigDecimal("-0.57"))
    }

    @Test
    fun roundedRoundsToTwoNumbersScale() {
        val notRoundedPercent = BigDecimal("0.5703")
        val rounded = rounded(notRoundedPercent)

        Truth.assertThat(rounded).isEqualTo(BigDecimal("0.57"))
    }

    @Test
    fun formatPercentageAppliesPercentagePatternWithEndingPercent() {
        val percent = BigDecimal(0.57)

        val formatPriceChange = formatPercentage(percent)

        Truth.assertThat(formatPriceChange).endsWith("%")
        Truth.assertThat(formatPriceChange).isEqualTo("+0.57%")
    }

    @Test
    fun formatPriceChangeDisplaysLeadingSignIfPositiveOrNegative() {
        val positiveBd1 = BigDecimal(12922.5)
        val negativeBd2 = BigDecimal(-12922.5)

        val formattedPlus = formatPriceChange(positiveBd1)
        val formattedMinus = formatPriceChange(negativeBd2)

        Truth.assertThat(formattedPlus).startsWith("+")
        Truth.assertThat(formattedMinus).startsWith("-")
    }
}