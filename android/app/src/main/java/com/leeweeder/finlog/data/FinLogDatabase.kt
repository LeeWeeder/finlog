package com.leeweeder.finlog.data

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.leeweeder.finlog.data.dao.TransactionDao
import com.leeweeder.finlog.data.dao.account.AccountDao
import com.leeweeder.finlog.data.dao.account.CreditAccountDao
import com.leeweeder.finlog.data.dao.account.DebitAccountDao
import com.leeweeder.finlog.data.entity.TransactionEntity
import com.leeweeder.finlog.data.entity.account.AccountEntity
import com.leeweeder.finlog.data.entity.account.CreditAccountEntity
import com.leeweeder.finlog.data.entity.account.CryptoAccountEntity
import com.leeweeder.finlog.data.entity.account.DebitAccountEntity

@Database(
    entities = [
        AccountEntity::class,
        DebitAccountEntity::class,
        CreditAccountEntity::class,
        CryptoAccountEntity::class,
        TransactionEntity::class
    ], version = 1,
    exportSchema = true
)
@ColumnTypeConverters(
    Converters::class
)
internal abstract class FinLogDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun debitAccountDao(): DebitAccountDao
    abstract fun creditAccountDao(): CreditAccountDao
    abstract fun transactionDao(): TransactionDao
}