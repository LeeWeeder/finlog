package com.leeweeder.finlog.domain.model.transaction

import com.leeweeder.finlog.domain.model.Amount
import kotlin.time.Instant

data class Transaction(
    val accountId: Long,
    val amount: Amount,
    val transactionType: TransactionType,
    val postedAt: Instant?,
    val createdAt: Instant
) {

    init {
        require(!amount.isNegative()) { "Transaction amount must not be negative" }
    }
}
