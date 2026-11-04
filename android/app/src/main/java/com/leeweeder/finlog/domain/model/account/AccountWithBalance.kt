package com.leeweeder.finlog.domain.model.account

import com.leeweeder.finlog.domain.model.Amount

data class AccountWithBalance(
    val account: Account,
    val balance: Amount
)
