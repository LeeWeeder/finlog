package com.leeweeder.finlog.data.entity.account

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey

@Entity(
    tableName = "crypto_accounts",
    foreignKeys = [ForeignKey(
        entity = AccountEntity::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
internal data class CryptoAccountEntity(
    @PrimaryKey
    override val accountId: Long,
    val assetSymbol: String
) : AccountSubtype