package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.account.DebitAccount

@Dao
internal interface DebitAccountDao {
    @Insert
    suspend fun insert(debitAccount: DebitAccount)
}