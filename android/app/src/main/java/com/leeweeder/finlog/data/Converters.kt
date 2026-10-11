package com.leeweeder.finlog.data

import androidx.room3.ColumnTypeConverter
import com.leeweeder.finlog.domain.model.Amount
import java.math.BigDecimal

class Converters {
    @ColumnTypeConverter
    fun fromAmount(a: Amount): String = a.value.toPlainString()

    @ColumnTypeConverter
    fun toAmount(s: String): Amount = Amount(BigDecimal(s))
}