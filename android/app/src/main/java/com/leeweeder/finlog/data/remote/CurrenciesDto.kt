package com.leeweeder.finlog.data.remote

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
internal data class CurrenciesDto(
    val schemaVersion: Int,
    val generatedAt: Instant,
    val fiatCurrencies: List<CurrencyDto.FiatCurrency>,
    val cryptoCurrencies: List<CurrencyDto.CryptoCurrency>
)


internal sealed interface CurrencyDto {
    val code: String
    val name: String

    @Serializable
    data class FiatCurrency(
        override val code: String,
        override val name: String,
        val digits: Int
    ) : CurrencyDto

    @Serializable
    data class CryptoCurrency(
        override val code: String,
        override val name: String
    ) : CurrencyDto

}