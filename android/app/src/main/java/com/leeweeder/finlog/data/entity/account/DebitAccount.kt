package com.leeweeder.finlog.data.entity.account

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.PrimaryKey
import com.leeweeder.finlog.domain.model.account.FiatAccountType

@Entity(
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["accountId"],
        onDelete = ForeignKey.CASCADE,
        onUpdate = ForeignKey.CASCADE
    )]
)
internal data class DebitAccount(
    @PrimaryKey
    override val accountId: Long,
    override val currencyCode: String
) : FiatAccount, AccountSubtype {

    override val fiatAccountType: FiatAccountType
        get() = FiatAccountType.Debit
}