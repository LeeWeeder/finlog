package com.leeweeder.finlog.data

import androidx.room3.ColumnTypeConverter
import com.leeweeder.finlog.domain.model.Amount
import java.math.BigDecimal
import kotlin.time.Instant

class Converters {
    @ColumnTypeConverter
    fun fromAmount(a: Amount): String = a.value.toPlainString()

    @ColumnTypeConverter
    fun toAmount(s: String): Amount = Amount(BigDecimal(s))

    @ColumnTypeConverter
    fun fromInstant(i: Instant): Long = i.toEpochMilliseconds()

    @ColumnTypeConverter
    fun toInstant(l: Long): Instant = Instant.fromEpochMilliseconds(l)
}