package com.leeweeder.finlog.data.di

import com.leeweeder.finlog.data.FinLogDatabase
import com.leeweeder.finlog.data.dao.account.AccountDao
import com.leeweeder.finlog.data.dao.account.DebitAccountDao
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
    fun providesDebitAccountDao(
        finLogDatabase: FinLogDatabase
    ): DebitAccountDao = finLogDatabase.debitAccountDao()
}