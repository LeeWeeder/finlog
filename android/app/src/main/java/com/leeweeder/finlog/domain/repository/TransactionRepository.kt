package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.TransactionType
import kotlin.time.Instant

interface TransactionRepository {
    suspend fun insertTransaction(
        accountId: Long,
        amount: Amount,
        type: TransactionType,
        postedAt: Instant? = null
    )
}