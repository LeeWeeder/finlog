package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.account.CreditAccountEntity

@Dao
internal interface CreditAccountDao : FiatAccountDao<CreditAccountEntity> {
    @Insert
    override suspend fun insert(account: CreditAccountEntity)
}