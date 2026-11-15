package com.leeweeder.finlog.data

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.leeweeder.finlog.data.dao.AccountDao
import com.leeweeder.finlog.data.dao.TransactionDao
import com.leeweeder.finlog.data.entity.AccountEntity
import com.leeweeder.finlog.data.entity.TransactionEntity

@Database(
    entities = [
        AccountEntity::class,
        TransactionEntity::class
    ], version = 1,
    exportSchema = true
)
@ColumnTypeConverters(
    Converters::class
)
internal abstract class FinLogDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}