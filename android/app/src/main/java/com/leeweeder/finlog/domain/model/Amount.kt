package com.leeweeder.finlog.domain.model

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Amount(val value: BigDecimal) {
    fun scaleTo(digits: Int): Amount = Amount(value.setScale(digits, RoundingMode.HALF_EVEN))

    val display: String
        get() = value.toPlainString()

    fun isNegative(): Boolean = value.signum() < 0

    operator fun plus(other: Amount) = Amount(value + other.value)
    operator fun minus(other: Amount) = Amount(value - other.value)
    operator fun unaryMinus() = Amount(value.negate())

    companion object {
        val ZERO = Amount(BigDecimal(0))
    }
}