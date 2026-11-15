package com.leeweeder.finlog.data.repository

import com.leeweeder.finlog.data.dao.AccountDao
import com.leeweeder.finlog.data.entity.AccountEntity
import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.model.account.AccountType
import com.leeweeder.finlog.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun insert(
        currencyCode: String,
        name: String,
        accountType: AccountType,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    ) {
        accountDao.insert(
            AccountEntity(
                type = accountType,
                currencyCode = currencyCode,
                name = name,
                isIncludedInNetBalance = isIncludedInNetBalance,
                iconKey = iconKey,
                decimalPlaces = decimalPlaces
            )
        )
    }

    override fun observeActiveAccounts(): Flow<List<Account>> {
        return accountDao.observeActive()
    }
}