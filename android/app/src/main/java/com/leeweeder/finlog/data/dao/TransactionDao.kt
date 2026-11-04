package com.leeweeder.finlog.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.leeweeder.finlog.data.entity.TransactionEntity
import com.leeweeder.finlog.domain.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity)

    @Query(
        """
        SELECT a.id AS accountId, amount, transactionType, postedAt, createdAt
        FROM transactions t
        JOIN accounts a
        ON a.id = t.accountId
        WHERE a.id IN (:accountIds)
    """
    )
    fun observeTransactionsOfAccounts(accountIds: Set<Long>): Flow<List<Transaction>>
}