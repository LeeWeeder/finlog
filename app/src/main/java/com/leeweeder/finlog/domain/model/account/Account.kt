package com.leeweeder.finlog.domain.model.account

data class Account(
    val id: Long,
    val type: AccountType,
    val name: String,
    val isIncludedInNetBalance: Boolean,
    val isArchived: Boolean,
) {
    companion object {
        fun creditAccount(name: String) {

        }
    }
}
