package com.leeweeder.finlog.data.repository

import com.leeweeder.finlog.data.dao.TransactionDao
import com.leeweeder.finlog.data.entity.TransactionEntity
import com.leeweeder.finlog.domain.model.transaction.Transaction
import com.leeweeder.finlog.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(
        transaction: Transaction
    ) {
        transactionDao.insert(
            TransactionEntity(
                accountId = transaction.accountId,
                amount = transaction.amount,
                transactionType = transaction.transactionType,
                createdAt = transaction.createdAt,
                postedAt = transaction.postedAt
            )
        )
    }

    override fun observeTransactionsOfAccounts(accountIds: Set<Long>): Flow<List<Transaction>> {
        return transactionDao.observeTransactionsOfAccounts(accountIds)
    }
}