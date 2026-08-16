package com.leeweeder.finlog.data.entity.account

import com.leeweeder.finlog.domain.model.account.FiatAccountType

internal interface FiatAccount {
    val currencyCode: String
    val fiatAccountType: FiatAccountType
}