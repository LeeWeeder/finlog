package com.leeweeder.finlog.data.repository

import androidx.room3.withWriteTransaction
import com.leeweeder.finlog.data.FinLogDatabase
import com.leeweeder.finlog.data.dao.account.AccountDao
import com.leeweeder.finlog.data.dao.account.DebitAccountDao
import com.leeweeder.finlog.data.entity.account.Account
import com.leeweeder.finlog.data.entity.account.DebitAccount
import com.leeweeder.finlog.domain.model.account.AccountType
import com.leeweeder.finlog.domain.model.account.FiatAccountType
import com.leeweeder.finlog.domain.repository.AccountRepository
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val finLogDatabase: FinLogDatabase,
    private val accountDao: AccountDao,
    private val debitAccountDao: DebitAccountDao
) : AccountRepository {
    override suspend fun insertFiatAccount(
        currencyCode: String,
        name: String,
        fiatAccountType: FiatAccountType,
        isIncludedInNetBalance: Boolean,
        isArchived: Boolean
    ) {
        finLogDatabase.withWriteTransaction {
            val accountId = accountDao.insert(
                Account(
                    type = AccountType.FiatAccount,
                    name = name,
                    isIncludedInNetBalance = isIncludedInNetBalance,
                    isArchived = isArchived
                )
            )

            debitAccountDao.insert(
                DebitAccount(
                    accountId = accountId,
                    currencyCode = currencyCode,
                    fiatAccountType = fiatAccountType
                )
            )
        }
    }
}