package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.account.FiatAccountType
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    suspend fun insertFiatAccount(
        currencyCode: String,
        name: String,
        fiatAccountType: FiatAccountType,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    )

    suspend fun insertCryptoAccount(
        assetSymbol: String,
        name: String,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    )
}