package com.leeweeder.finlog.domain.model.currency

@ConsistentCopyVisibility
data class Currencies private constructor(
    val fiatCurrencies: List<Currency.FiatCurrency>,
    val cryptoCurrencies: List<Currency.CryptoCurrency>
) {
    constructor(
        fiatCurrencies: Set<Currency.FiatCurrency>,
        cryptoCurrency: Set<Currency.CryptoCurrency>
    ) : this(fiatCurrencies.toList(), cryptoCurrency.toList())
}

sealed interface Currency {
    val code: String
    val name: String

    data class FiatCurrency(
        override val code: String,
        override val name: String,
        val digits: Int
    ) : Currency

    data class CryptoCurrency(
        override val code: String,
        override val name: String
    ) : Currency

}