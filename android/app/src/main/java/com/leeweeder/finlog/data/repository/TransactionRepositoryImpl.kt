package com.leeweeder.finlog.data.repository

import com.leeweeder.finlog.data.dao.TransactionDao
import com.leeweeder.finlog.data.entity.Transaction
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.TransactionType
import com.leeweeder.finlog.domain.repository.TransactionRepository
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Instant

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun insertTransaction(
        accountId: Long,
        amount: Amount,
        type: TransactionType,
        postedAt: Instant?
    ) {
        transactionDao.insert(
            Transaction(
                accountId = accountId,
                amount = amount,
                transactionType = type,
                createdAtMillis = Clock.System.now().toEpochMilliseconds(),
                postedAtMillis = postedAt?.toEpochMilliseconds()
            )
        )
    }
}