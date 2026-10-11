package com.leeweeder.finlog.data.remote

import retrofit2.http.GET

internal interface CurrenciesApi {
    @GET("currencies")
    suspend fun getCurrencies(): CurrenciesDto
}