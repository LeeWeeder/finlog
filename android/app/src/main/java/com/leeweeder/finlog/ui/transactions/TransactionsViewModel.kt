package com.leeweeder.finlog.ui.transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeweeder.finlog.domain.model.Amount
import com.leeweeder.finlog.domain.model.account.Account
import com.leeweeder.finlog.domain.model.transaction.Transaction
import com.leeweeder.finlog.domain.model.transaction.TransactionType
import com.leeweeder.finlog.domain.repository.AccountRepository
import com.leeweeder.finlog.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Instant

@HiltViewModel
internal class TransactionsViewModel @Inject constructor(
    accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val transactionDraft: MutableStateFlow<TransactionDraft> =
        MutableStateFlow(TransactionDraft.Standalone())

    val uiState = combine(
        accountRepository.observeActiveAccounts(),
        transactionDraft
    ) { activeAccounts, draft ->
        TransactionsUiState.Success(
            draft = when (draft) {
                is TransactionDraft.Standalone -> draft.copy(
                    selectedAccount = activeAccounts.first()
                )

                is TransactionDraft.Transfer -> draft.copy(
                    fromAccount = activeAccounts.first()
                )
            },
            availableAccounts = activeAccounts,
        )
    }.catch { throwable ->
        TransactionsUiState.Error(throwable)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TransactionsUiState.Loading
    )

    fun onEvent(event: TransactionEvent) {
        when (event) {
            TransactionEvent.Save -> {
                viewModelScope.launch {
                    when (val draft = transactionDraft.value) {
                        is TransactionDraft.Standalone -> {
                            transactionRepository.insertTransaction(
                                Transaction(
                                    accountId = draft.selectedAccount!!.id,
                                    amount = draft.amount,
                                    transactionType = when (draft.type) {
                                        StandaloneTransactionType.Expense -> TransactionType.Expense
                                        StandaloneTransactionType.Income -> TransactionType.Income
                                    },
                                    postedAt = draft.postedAt,
                                    createdAt = draft.createdAt
                                )
                            )
                        }

                        is TransactionDraft.Transfer -> TODO()
                    }
                }
            }

            is TransactionEvent.SetCreatedAt -> {
                transactionDraft.update {
                    it.withCreatedAt(event.createdAt)
                }
            }

            is TransactionEvent.SetPostedAt -> {
                transactionDraft.update {
                    it.withPostedAt(event.postedAt)
                }
            }

            is TransactionEvent.SetTitle -> {
                transactionDraft.update {
                    it.withTitle(event.title)
                }
            }

            is TransactionEvent.StandaloneTransactionEvent.SetAccount -> {
                transactionDraft.update {
                    (it as TransactionDraft.Standalone).copy(
                        selectedAccount = event.account
                    )
                }
            }

            is TransactionEvent.StandaloneTransactionEvent.SetAmount -> {
                transactionDraft.update {
                    (it as TransactionDraft.Standalone).copy(
                        amount = event.amount
                    )
                }
            }

            is TransactionEvent.StandaloneTransactionEvent.SetType -> {
                transactionDraft.update {
                    (it as TransactionDraft.Standalone).copy(
                        type = event.type
                    )
                }
            }

            TransactionEvent.ToggleIsPosted -> {
                transactionDraft.update {
                    it.withIsPosted(!it.isPosted)
                }
            }

            is TransactionEvent.TransferTransactionEvent.SetAccount -> {
                transactionDraft.update {
                    val draft = it as TransactionDraft.Transfer

                    when (event.role) {
                        TransactionEvent.TransferTransactionEvent.Role.Source -> draft.copy(
                            fromAccount = event.account
                        )

                        TransactionEvent.TransferTransactionEvent.Role.Destination -> draft.copy(
                            toAccount = event.account
                        )
                    }
                }
            }

            is TransactionEvent.TransferTransactionEvent.SetAmount -> {
                transactionDraft.update {
                    val draft = it as TransactionDraft.Transfer

                    when (event.role) {
                        TransactionEvent.TransferTransactionEvent.Role.Source -> draft.copy(
                            amountSent = event.amount
                        )

                        TransactionEvent.TransferTransactionEvent.Role.Destination -> draft.copy(
                            amountReceived = event.amount
                        )
                    }
                }
            }

            is TransactionEvent.SetTransactionType -> {
                transactionDraft.update {
                    when (event.type) {
                        com.leeweeder.finlog.ui.transactions.TransactionType.Expense -> {
                            it.toStandalone(StandaloneTransactionType.Expense)
                        }

                        com.leeweeder.finlog.ui.transactions.TransactionType.Income -> {
                            it.toStandalone(StandaloneTransactionType.Income)
                        }

                        com.leeweeder.finlog.ui.transactions.TransactionType.Transfer -> {
                            it.toTransfer()
                        }
                    }
                }
            }
        }
    }
}

internal sealed interface TransactionEvent {
    data class SetTitle(val title: String) : TransactionEvent
    data class SetCreatedAt(val createdAt: Instant) : TransactionEvent
    data object ToggleIsPosted : TransactionEvent
    data class SetPostedAt(val postedAt: Instant) : TransactionEvent

    data class SetTransactionType(val type: com.leeweeder.finlog.ui.transactions.TransactionType) :
        TransactionEvent

    data object Save : TransactionEvent

    sealed interface StandaloneTransactionEvent : TransactionEvent {
        data class SetAccount(val account: Account) : StandaloneTransactionEvent
        data class SetType(val type: StandaloneTransactionType) : StandaloneTransactionEvent
        data class SetAmount(val amount: Amount) : StandaloneTransactionEvent
    }

    sealed interface TransferTransactionEvent : TransactionEvent {
        enum class Role {
            Source,
            Destination
        }

        data class SetAccount(val account: Account, val role: Role) : TransferTransactionEvent
        data class SetAmount(val amount: Amount, val role: Role) : TransferTransactionEvent

    }
}

internal sealed interface TransactionDraft {
    val title: String
    val createdAt: Instant
    val isPosted: Boolean
    val postedAt: Instant?

    fun withTitle(title: String): TransactionDraft
    fun withCreatedAt(createdAt: Instant): TransactionDraft
    fun withIsPosted(isPosted: Boolean): TransactionDraft
    fun withPostedAt(postedAt: Instant): TransactionDraft

    fun toStandalone(type: StandaloneTransactionType): Standalone
    fun toTransfer(): Transfer

    data class Standalone(
        override val title: String = "",
        override val createdAt: Instant = Clock.System.now(),
        override val isPosted: Boolean = false,
        override val postedAt: Instant? = if (isPosted) createdAt else null,
        val type: StandaloneTransactionType = StandaloneTransactionType.Expense,
        val amount: Amount = Amount.ZERO,
        val selectedAccount: Account? = null,
    ) : TransactionDraft {
        override fun withTitle(title: String): TransactionDraft {
            return copy(title = title)
        }

        override fun withCreatedAt(createdAt: Instant): TransactionDraft {
            return copy(createdAt = createdAt)
        }

        override fun withIsPosted(isPosted: Boolean): TransactionDraft {
            return copy(isPosted = isPosted)
        }

        override fun withPostedAt(postedAt: Instant): TransactionDraft {
            return copy(postedAt = postedAt)
        }

        override fun toStandalone(type: StandaloneTransactionType): Standalone {
            return copy(type = type)
        }

        override fun toTransfer(): Transfer {
            return Transfer(
                title = title,
                createdAt = createdAt,
                isPosted = isPosted,
                postedAt = postedAt,
                fromAccount = selectedAccount,
                amountSent = amount
            )
        }
    }

    data class Transfer(
        override val title: String = "",
        override val createdAt: Instant = Clock.System.now(),
        override val isPosted: Boolean = false,
        override val postedAt: Instant? = if (isPosted) createdAt else null,
        val fromAccount: Account? = null,
        val toAccount: Account? = null,
        val amountSent: Amount = Amount.ZERO,
        val amountReceived: Amount = amountSent
    ) : TransactionDraft {
        override fun withTitle(title: String): TransactionDraft {
            return copy(title = title)
        }

        override fun withCreatedAt(createdAt: Instant): TransactionDraft {
            return copy(createdAt = createdAt)
        }

        override fun withIsPosted(isPosted: Boolean): TransactionDraft {
            return copy(isPosted = isPosted)
        }

        override fun withPostedAt(postedAt: Instant): TransactionDraft {
            return copy(postedAt = postedAt)
        }

        override fun toStandalone(type: StandaloneTransactionType): Standalone {
            return Standalone(
                title = title,
                createdAt = createdAt,
                isPosted = isPosted,
                postedAt = postedAt,
                selectedAccount = fromAccount,
                amount = amountSent,
                type = type
            )
        }

        override fun toTransfer(): Transfer = this
    }
}

internal sealed interface TransactionsUiState {
    data class Success(
        val draft: TransactionDraft,
        val availableAccounts: List<Account>,
    ) : TransactionsUiState

    data object Loading : TransactionsUiState
    data class Error(var throwable: Throwable) : TransactionsUiState
}

internal enum class TransactionType {
    Expense,
    Income,
    Transfer
}

internal enum class StandaloneTransactionType {
    Expense,
    Income
}