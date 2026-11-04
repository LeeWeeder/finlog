package com.leeweeder.finlog.domain.model.transaction

enum class TransactionType(val sign: Int) {
    Income(+1),
    Expense(-1),
    AdjustmentIncrease(+1),
    AdjustmentDecrease(-1),
    TransferIn(+1),
    TransferOut(-1)

}