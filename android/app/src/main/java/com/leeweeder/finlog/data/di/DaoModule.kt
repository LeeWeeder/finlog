package com.leeweeder.finlog.data.di

import com.leeweeder.finlog.data.FinLogDatabase
import com.leeweeder.finlog.data.dao.AccountDao
import com.leeweeder.finlog.data.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaoModule {
    @Provides
    fun providesAccountDao(
        finLogDatabase: FinLogDatabase
    ): AccountDao = finLogDatabase.accountDao()

    @Provides
    fun providesTransactionDao(
        finLogDatabase: FinLogDatabase
    ): TransactionDao = finLogDatabase.transactionDao()
}