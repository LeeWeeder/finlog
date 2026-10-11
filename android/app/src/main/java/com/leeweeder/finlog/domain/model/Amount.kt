package com.leeweeder.finlog.domain.model

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Amount(val value: BigDecimal) {
    fun scaleTo(digits: Int): Amount = Amount(value.setScale(digits, RoundingMode.HALF_EVEN))

    val display: String
        get() = value.toPlainString()

    operator fun plus(other: Amount) = Amount(value + other.value)
    operator fun minus(other: Amount) = Amount(value - other.value)
}