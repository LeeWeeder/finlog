package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.account.CreditAccount

@Dao
internal interface CreditAccountDao : FiatAccountDao<CreditAccount> {
    @Insert
    override suspend fun insert(account: CreditAccount)
}