package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.account.DebitAccount

@Dao
internal interface DebitAccountDao : FiatAccountDao<DebitAccount> {
    @Insert
    override suspend fun insert(account: DebitAccount)
}