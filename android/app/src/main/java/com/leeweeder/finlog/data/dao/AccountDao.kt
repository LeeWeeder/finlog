package com.leeweeder.finlog.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.leeweeder.finlog.data.entity.AccountEntity
import com.leeweeder.finlog.domain.model.account.Account
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AccountDao {
    @Insert
    suspend fun insert(account: AccountEntity): Long

    @Query(
        """
        SELECT 
            id AS accountId,
            name,
            type,
            currencyCode,
            decimalPlaces,
            isIncludedInNetBalance
        FROM accounts
        WHERE isArchived = 0
    """
    )
    fun observeActive(): Flow<List<Account>>
}