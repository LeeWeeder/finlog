package com.leeweeder.finlog.data.repository

import android.database.sqlite.SQLiteConstraintException
import com.leeweeder.finlog.data.dao.AccountDao
import com.leeweeder.finlog.data.entity.AccountEntity
import com.leeweeder.finlog.data.entity.toDomain
import com.leeweeder.finlog.domain.exception.account.AccountException
import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.repository.AccountEditFields
import com.leeweeder.finlog.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun insert(fields: AccountEditFields) {
        try {
            accountDao.insert(
                AccountEntity(
                    type = fields.accountType,
                    currencyCode = fields.currencyCode,
                    name = fields.name,
                    isIncludedInNetBalance = fields.isIncludedInNetBalance,
                    iconKey = fields.iconKey,
                    decimalPlaces = fields.decimalPlaces
                )
            )
        } catch (_: SQLiteConstraintException) {
            throw AccountException.NameTaken()
        }
    }

    override suspend fun update(
        accountId: Long,
        fields: AccountEditFields
    ) {
        try {
            accountDao.update(
                accountId = accountId,
                accountType = fields.accountType,
                newName = fields.name,
                currencyCode = fields.currencyCode,
                decimalPlaces = fields.decimalPlaces,
                isIncludedInNetBalance = fields.isIncludedInNetBalance,
                iconKey = fields.iconKey,
            )
        } catch (_: SQLiteConstraintException) {
            throw AccountException.NameTaken()
        }
    }

    override suspend fun delete(accountId: Long) {
        accountDao.delete(accountId)
    }

    override suspend fun getById(accountId: Long): Account? {
        return accountDao.getById(accountId)?.toDomain()
    }

    override fun observeActiveAccounts(): Flow<List<Account>> {
        return accountDao.observeActive().map { accounts -> accounts.map { it.toDomain() } }
    }
}