package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)

    fun observeTransactionsOfAccounts(accountIds: Set<Long>): Flow<List<Transaction>>
}