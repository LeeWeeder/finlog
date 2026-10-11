package com.leeweeder.finlog.data.entity.account

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.domain.model.account.AccountType

@Entity
internal data class Account(
    val type: AccountType,
    val name: String,
    val isIncludedInNetBalance: Boolean,
    val isArchived: Boolean = false,
    val iconKey: String,
    val decimalPlaces: Int
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}