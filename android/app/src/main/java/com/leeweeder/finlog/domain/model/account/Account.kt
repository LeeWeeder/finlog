package com.leeweeder.finlog.domain.model.account

import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.transaction.Transaction
import com.leeweeder.finlog.domain.model.transaction.TransactionType
import kotlin.time.Clock
import kotlin.time.Instant

data class Account(
    val accountId: Long,
    val name: String,
    val code: String,
    val decimalPlaces: Int
) {
    fun newTransaction(
        amount: Amount,
        transactionType: TransactionType,
        postedAt: Instant? = null,
        createdAt: Instant = Clock.System.now()
    ): Transaction {
        return Transaction(
            accountId = accountId,
            amount = amount.scaleTo(decimalPlaces),
            transactionType = transactionType,
            postedAt = postedAt,
            createdAt = createdAt
        )
    }
}
