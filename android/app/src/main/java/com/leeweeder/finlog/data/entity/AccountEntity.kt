package com.leeweeder.finlog.data.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.domain.model.account.AccountType

@Entity(tableName = "accounts")
internal data class AccountEntity(
    val type: AccountType,
    val name: String,
    val currencyCode: String,
    val isIncludedInNetBalance: Boolean,
    val isArchived: Boolean = false,
    val iconKey: String,
    val decimalPlaces: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}