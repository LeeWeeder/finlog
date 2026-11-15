package com.leeweeder.finlog.domain.exception

open class DomainException(
    override val message: String,
    open val innerException: Throwable? = null
) :
    Exception()