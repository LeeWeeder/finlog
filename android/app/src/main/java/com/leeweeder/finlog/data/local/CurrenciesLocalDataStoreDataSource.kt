package com.leeweeder.finlog.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CurrenciesLocalDataStoreDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val json: Json
) {
    private val currenciesKey = stringPreferencesKey("cached_currencies")

    val cachedCurrencies: Flow<CurrenciesLocal?> = dataStore.data.map { preferences ->
        preferences[currenciesKey]?.let {
            json.decodeFromString<CurrenciesLocal>(it)
        }
    }

    suspend fun saveCurrencies(currencies: CurrenciesLocal) {
        dataStore.edit { preferences ->
            preferences[currenciesKey] = json.encodeToString(currencies)
        }
    }
}