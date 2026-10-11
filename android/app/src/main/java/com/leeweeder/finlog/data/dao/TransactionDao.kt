package com.leeweeder.finlog.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import com.leeweeder.finlog.data.entity.Transaction

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)
}