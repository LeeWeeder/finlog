package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.account.Account

@Dao
internal interface AccountDao {
    @Insert
    suspend fun insert(account: Account): Long
}