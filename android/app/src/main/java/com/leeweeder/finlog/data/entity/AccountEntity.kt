package com.leeweeder.finlog.data.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.model.account.AccountType

@Entity(tableName = "accounts", indices = [Index("name", unique = true)])
internal data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: AccountType,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val name: String,
    val currencyCode: String,
    val isIncludedInNetBalance: Boolean,
    val isArchived: Boolean = false,
    val iconKey: String,
    val decimalPlaces: Int
)

internal fun AccountEntity.toDomain() = Account(
    id,
    type,
    name,
    currencyCode,
    decimalPlaces,
    isIncludedInNetBalance
)