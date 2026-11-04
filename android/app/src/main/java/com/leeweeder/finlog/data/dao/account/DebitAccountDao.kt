package com.leeweeder.finlog.data.dao.account

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.Query
import com.leeweeder.finlog.data.entity.account.DebitAccountEntity
import com.leeweeder.finlog.domain.model.account.Account
import kotlinx.coroutines.flow.Flow

@Dao
internal interface DebitAccountDao : FiatAccountDao<DebitAccountEntity> {
    @Insert
    override suspend fun insert(account: DebitAccountEntity)

    @Query(
        """
        SELECT 
            a.id AS accountId,
            a.name,
            da.currencyCode AS code,
            a.decimalPlaces
        FROM accounts a
        JOIN debit_accounts da
        ON a.id = da.accountId
        WHERE a.isArchived = 0
    """
    )
    fun observeActive(): Flow<List<Account>>
}