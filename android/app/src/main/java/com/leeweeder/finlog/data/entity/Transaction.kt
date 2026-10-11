package com.leeweeder.finlog.data.entity

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.data.entity.account.Account
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.TransactionType

@Entity(
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )],
    indices = [
        Index("accountId")
    ]
)
data class Transaction(
    val accountId: Long,
    val amount: Amount,
    val transactionType: TransactionType,
    val createdAtMillis: Long,
    val postedAtMillis: Long?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
