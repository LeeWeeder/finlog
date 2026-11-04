package com.leeweeder.finlog.data.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.data.entity.account.AccountEntity
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.transaction.TransactionType
import kotlin.time.Instant

@Entity(
    tableName = "transactions",
    foreignKeys = [ForeignKey(
        entity = AccountEntity::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [
        Index("accountId")
    ]
)
data class TransactionEntity(
    val accountId: Long,
    val amount: Amount,
    val transactionType: TransactionType,
    val createdAt: Instant,
    val postedAt: Instant?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
