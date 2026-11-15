package com.leeweeder.finlog.domain.use_case

import com.leeweeder.finlog.domain.logic.BalanceCalculator
import com.leeweeder.finlog.domain.model.account.AccountWithBalance
import com.leeweeder.finlog.domain.repository.AccountRepository
import com.leeweeder.finlog.domain.repository.TransactionRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetActiveAccountsWithBalanceUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    private val balanceCalculator = BalanceCalculator()

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<AccountWithBalance>> {
        return accountRepository.observeActiveAccounts()
            .map { accounts -> accounts.associateBy { it.id } }
            .flatMapLatest { activeAccounts ->
                transactionRepository
                    .observeTransactionsOfAccounts(activeAccounts.keys)
                    .map { transactions ->
                        val byAccount = transactions.groupBy { it.accountId }
                        activeAccounts.values.map { account ->
                            AccountWithBalance(
                                account = account,
                                balance = balanceCalculator.calculateBalance(
                                    byAccount[account.id].orEmpty(),
                                    account.decimalPlaces
                                )
                            )
                        }
                    }
            }
    }
}