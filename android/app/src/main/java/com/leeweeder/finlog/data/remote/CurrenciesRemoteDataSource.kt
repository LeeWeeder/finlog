package com.leeweeder.finlog.data.remote

import javax.inject.Inject

internal class CurrenciesRemoteDataSource @Inject constructor(
    private val api: CurrenciesApi
) {
    suspend fun fetchCurrencies(): CurrenciesDto = api.getCurrencies()
}