package com.leeweeder.finlog.data.dao.account

import com.leeweeder.finlog.data.entity.account.FiatAccount

internal sealed interface FiatAccountDao<T : FiatAccount> {
    suspend fun insert(account: T)
}