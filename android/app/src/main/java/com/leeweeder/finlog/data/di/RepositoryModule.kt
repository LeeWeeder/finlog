package com.leeweeder.finlog.data.di

import com.leeweeder.finlog.data.repository.AccountRepositoryImpl
import com.leeweeder.finlog.data.repository.CurrenciesRepositoryImpl
import com.leeweeder.finlog.data.repository.TransactionRepositoryImpl
import com.leeweeder.finlog.domain.repository.AccountRepository
import com.leeweeder.finlog.domain.repository.CurrenciesRepository
import com.leeweeder.finlog.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindsAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    abstract fun bindsCurrencyRepository(impl: CurrenciesRepositoryImpl): CurrenciesRepository

    @Binds
    abstract fun bindsTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository
}