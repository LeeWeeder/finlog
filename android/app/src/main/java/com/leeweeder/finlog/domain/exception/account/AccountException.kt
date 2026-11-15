package com.leeweeder.finlog.domain.exception.account

import com.leeweeder.finlog.domain.exception.DomainException

sealed class AccountException(
    override val message: String,
    override val innerException: Throwable? = null
) : DomainException(message, innerException) {
    class NameTaken : AccountException(this.MESSAGE) {
        companion object {
            const val MESSAGE = "Account name already exists."
        }
    }
}
