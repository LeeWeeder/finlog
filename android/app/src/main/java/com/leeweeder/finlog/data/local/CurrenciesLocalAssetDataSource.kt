package com.leeweeder.finlog.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CurrenciesLocalAssetDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val json: Json
) {
    fun loadSeedCurrencies(): CurrenciesLocal {
        val jsonText = context.assets
            .open("currencies.json")
            .bufferedReader()
            .use { it.readText() }

        return json.decodeFromString(jsonText)
    }
}