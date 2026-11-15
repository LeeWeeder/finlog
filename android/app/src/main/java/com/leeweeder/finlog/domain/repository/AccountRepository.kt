package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.model.account.AccountType
import kotlinx.coroutines.flow.Flow

internal interface AccountRepository {
    suspend fun insert(fields: AccountEditFields)

    suspend fun update(
        accountId: Long,
        fields: AccountEditFields
    )

    suspend fun delete(accountId: Long)

    suspend fun getById(accountId: Long): Account?

    fun observeActiveAccounts(): Flow<List<Account>>
}

internal data class AccountEditFields(
    val name: String,
    val currencyCode: String,
    val accountType: AccountType,
    val isIncludedInNetBalance: Boolean,
    val iconKey: String,
    val decimalPlaces: Int
)