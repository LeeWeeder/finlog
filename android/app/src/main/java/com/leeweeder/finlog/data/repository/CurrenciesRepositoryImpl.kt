package com.leeweeder.finlog.data.repository

import androidx.datastore.core.IOException
import com.leeweeder.finlog.data.local.CurrenciesLocal
import com.leeweeder.finlog.data.local.CurrenciesLocalAssetDataSource
import com.leeweeder.finlog.data.local.CurrenciesLocalDataStoreDataSource
import com.leeweeder.finlog.data.local.CurrencyLocal
import com.leeweeder.finlog.data.remote.CurrenciesRemoteDataSource
import com.leeweeder.finlog.domain.model.currency.Currencies
import com.leeweeder.finlog.domain.model.currency.Currency
import com.leeweeder.finlog.domain.repository.CurrenciesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class CurrenciesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CurrenciesRemoteDataSource,
    private val localDataStoreDataSource: CurrenciesLocalDataStoreDataSource,
    private val localAssetDataSource: CurrenciesLocalAssetDataSource
) : CurrenciesRepository {
    override fun getCurrencies(): Flow<Currencies> = flow {
        val cachedLocalCurrencies = localDataStoreDataSource.cachedCurrencies.first()

        val quickLoad = cachedLocalCurrencies?.toDomain()
            ?: localAssetDataSource.loadSeedCurrencies().toDomain()

        emit(quickLoad)

        try {
            val dto = remoteDataSource.fetchCurrencies()
            val local = CurrenciesLocal(
                generatedAt = dto.generatedAt,
                fiatCurrencies = dto.fiatCurrencies.map { CurrencyLocal.FiatCurrency(it.code, it.name, it.digits) },
                cryptoCurrencies = dto.cryptoCurrencies.map { CurrencyLocal.CryptoCurrency(it.code, it.name) }
            )
            localDataStoreDataSource.saveCurrencies(local)

            emit(local.toDomain())
        } catch (_: IOException) {
            localAssetDataSource.loadSeedCurrencies().toDomain()
        }
    }
}

private fun CurrenciesLocal.toDomain(): Currencies {
    return Currencies(
        fiatCurrencies = fiatCurrencies.map { Currency.FiatCurrency(it.code, it.name, it.digits) }.toSet(),
        cryptoCurrency = cryptoCurrencies.map { Currency.CryptoCurrency(it.code, it.name) }.toSet())
}