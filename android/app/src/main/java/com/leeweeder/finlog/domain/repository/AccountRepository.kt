package com.leeweeder.finlog.domain.repository

import com.leeweeder.finlog.domain.model.account.FiatAccountType

interface AccountRepository {
    suspend fun insertFiatAccount(
        currencyCode: String,
        name: String,
        fiatAccountType: FiatAccountType,
        isIncludedInNetBalance: Boolean,
        isArchived: Boolean
    )
}