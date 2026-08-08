package com.leeweeder.finlog.data.di

import android.content.Context
import androidx.room3.Room
import com.leeweeder.finlog.data.FinLogDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesFinLogDatabase(
        @ApplicationContext context: Context
    ): FinLogDatabase = Room.databaseBuilder(
        context,
        FinLogDatabase::class.java,
        "finlog-database"
    ).build()
}