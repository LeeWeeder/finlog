package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.currency.Currencies
import kotlinx.coroutines.flow.Flow

interface CurrenciesRepository {
    fun getCurrencies(): Flow<Currencies>
}