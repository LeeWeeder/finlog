package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.model.account.AccountType
import kotlinx.coroutines.flow.Flow

internal interface AccountRepository {
    suspend fun insert(
        currencyCode: String,
        name: String,
        accountType: AccountType,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    )

    fun observeActiveAccounts(): Flow<List<Account>>
}