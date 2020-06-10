package com.assignment.core.data.utils

import com.assignment.core.domain.model.update.UpdateEventType
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal

internal class MoshiAdapter {

    @FromJson
    fun getUpdateEventType(string: String): UpdateEventType {
        UpdateEventType.values()
            .find { it.value == string }
            ?.let { eventType -> return eventType }
        return UpdateEventType.OTHER
    }

    @FromJson
    fun stringToBigDecimal(string: String): BigDecimal {
        return string.toBigDecimal()
    }

    @ToJson
    fun bigDecimalToPlainString(bigDecimal: BigDecimal): String {
        return bigDecimal.toPlainString()
    }
}
