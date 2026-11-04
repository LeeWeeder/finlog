package com.leeweeder.finlog.domain.logic

import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.transaction.Transaction
import com.leeweeder.finlog.domain.model.transaction.TransactionType
import java.math.RoundingMode

class BalanceCalculator {
    fun calculateBalance(transactions: List<Transaction>, digits: Int): Amount {
        val sum = transactions.sumOf {
            if (it.transactionType == TransactionType.AdjustmentIncrease || it.transactionType == TransactionType.Income)
                it.amount.value
            else
                -it.amount.value
        }

        return Amount(sum.setScale(digits, RoundingMode.HALF_EVEN))
    }
}