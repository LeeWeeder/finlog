package com.leeweeder.finlog.data

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.leeweeder.finlog.data.dao.TransactionDao
import com.leeweeder.finlog.data.dao.account.AccountDao
import com.leeweeder.finlog.data.dao.account.CreditAccountDao
import com.leeweeder.finlog.data.dao.account.DebitAccountDao
import com.leeweeder.finlog.data.entity.Transaction
import com.leeweeder.finlog.data.entity.account.Account
import com.leeweeder.finlog.data.entity.account.CreditAccount
import com.leeweeder.finlog.data.entity.account.CryptoAccount
import com.leeweeder.finlog.data.entity.account.DebitAccount

@Database(
    entities = [
        Account::class,
        DebitAccount::class,
        CreditAccount::class,
        CryptoAccount::class,
        Transaction::class
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