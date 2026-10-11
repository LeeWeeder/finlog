package com.leeweeder.finlog.data.local

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
internal data class CurrenciesLocal(
    val generatedAt: Instant,
    val fiatCurrencies: List<CurrencyLocal.FiatCurrency>,
    val cryptoCurrencies: List<CurrencyLocal.CryptoCurrency>
)

internal sealed interface CurrencyLocal {
    val code: String
    val name: String

    @Serializable
    data class FiatCurrency(
        override val code: String,
        override val name: String,
        val digits: Int
    ) : CurrencyLocal

    @Serializable
    data class CryptoCurrency(
        override val code: String,
        override val name: String
    ) : CurrencyLocal

}