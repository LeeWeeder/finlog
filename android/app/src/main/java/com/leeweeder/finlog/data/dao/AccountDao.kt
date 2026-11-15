package com.leeweeder.finlog.data.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.leeweeder.finlog.data.entity.AccountEntity
import com.leeweeder.finlog.domain.model.account.AccountType
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AccountDao {
    @Insert
    suspend fun insert(account: AccountEntity): Long

    @Query(
        """
            UPDATE accounts
            SET
                type = :accountType, 
                name = :newName, 
                currencyCode = :currencyCode,
                decimalPlaces = :decimalPlaces,
                isIncludedInNetBalance = :isIncludedInNetBalance,
                iconKey = :iconKey
            WHERE id = :accountId
        """
    )
    suspend fun update(
        accountId: Long,
        accountType: AccountType,
        newName: String,
        currencyCode: String,
        decimalPlaces: Int,
        isIncludedInNetBalance: Boolean,
        iconKey: String
    ): Int

    @Query("DELETE FROM accounts WHERE id = :accountId")
    suspend fun delete(accountId: Long)

    @Query("SELECT * FROM accounts WHERE id = :accountId")
    suspend fun getById(accountId: Long): AccountEntity?

    @Query("SELECT * FROM accounts WHERE isArchived = 0")
    fun observeActive(): Flow<List<AccountEntity>>
}