package com.leeweeder.finlog.data.repository

import androidx.room3.withWriteTransaction
import com.leeweeder.finlog.data.FinLogDatabase
import com.leeweeder.finlog.data.dao.account.AccountDao
import com.leeweeder.finlog.data.dao.account.CreditAccountDao
import com.leeweeder.finlog.data.dao.account.DebitAccountDao
import com.leeweeder.finlog.data.entity.account.Account
import com.leeweeder.finlog.data.entity.account.CreditAccount
import com.leeweeder.finlog.data.entity.account.DebitAccount
import com.leeweeder.finlog.domain.model.account.AccountType
import com.leeweeder.finlog.domain.model.account.FiatAccountType
import com.leeweeder.finlog.domain.repository.AccountRepository
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val finLogDatabase: FinLogDatabase,
    private val accountDao: AccountDao,
    private val debitAccountDao: DebitAccountDao,
    private val creditAccountDao: CreditAccountDao
) : AccountRepository {
    override suspend fun insertFiatAccount(
        currencyCode: String,
        name: String,
        fiatAccountType: FiatAccountType,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    ) {
        finLogDatabase.withWriteTransaction {
            val accountId = accountDao.insert(
                Account(
                    type = AccountType.Fiat,
                    name = name,
                    isIncludedInNetBalance = isIncludedInNetBalance,
                    iconKey = iconKey,
                    decimalPlaces = decimalPlaces
                )
            )

            when (fiatAccountType) {
                FiatAccountType.Debit -> {
                    debitAccountDao.insert(
                        DebitAccount(
                            accountId = accountId,
                            currencyCode = currencyCode
                        )
                    )
                }

                FiatAccountType.Credit -> {
                    creditAccountDao.insert(
                        CreditAccount(
                            accountId = accountId,
                            currencyCode = currencyCode
                        )
                    )
                }
            }
        }
    }

    override suspend fun insertCryptoAccount(
        assetSymbol: String,
        name: String,
        isIncludedInNetBalance: Boolean,
        decimalPlaces: Int,
        iconKey: String
    ) {
        TODO("Not yet implemented")
    }
}