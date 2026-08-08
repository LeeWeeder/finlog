package com.leeweeder.finlog.data.di

import com.leeweeder.finlog.data.repository.AccountRepositoryImpl
import com.leeweeder.finlog.domain.repository.AccountRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    abstract fun bindsAccountRepository(impl: AccountRepositoryImpl): AccountRepository
}