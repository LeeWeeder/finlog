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
internal class TransactionFormViewModel @Inject constructor(
    accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) : ViewModel() {
    private val transactionDraft: MutableStateFlow<TransactionFormDraft> =
        MutableStateFlow(TransactionFormDraft.Standalone())

    val uiState = combine(
        accountRepository.observeActiveAccounts(),
        transactionDraft
    ) { activeAccounts, draft ->
        TransactionFormUiState.Success(
            draft = when (draft) {
                is TransactionFormDraft.Standalone -> draft.copy(
                    selectedAccount = activeAccounts.first()
                )

                is TransactionFormDraft.Transfer -> draft.copy(
                    fromAccount = activeAccounts.first()
                )
            },
            availableAccounts = activeAccounts,
        )
    }.catch { throwable ->
        TransactionFormUiState.Error(throwable)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = TransactionFormUiState.Loading
    )

    fun onEvent(event: TransactionFormEvent) {
        when (event) {
            TransactionFormEvent.Save -> {
                viewModelScope.launch {
                    when (val draft = transactionDraft.value) {
                        is TransactionFormDraft.Standalone -> {
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

                        is TransactionFormDraft.Transfer -> TODO()
                    }
                }
            }

            is TransactionFormEvent.SetCreatedAt -> {
                transactionDraft.update {
                    it.withCreatedAt(event.createdAt)
                }
            }

            is TransactionFormEvent.SetPostedAt -> {
                transactionDraft.update {
                    it.withPostedAt(event.postedAt)
                }
            }

            is TransactionFormEvent.SetTitle -> {
                transactionDraft.update {
                    it.withTitle(event.title)
                }
            }

            is TransactionFormEvent.Standalone.SetAccount -> {
                transactionDraft.update {
                    (it as TransactionFormDraft.Standalone).copy(
                        selectedAccount = event.account
                    )
                }
            }

            is TransactionFormEvent.Standalone.SetAmount -> {
                transactionDraft.update {
                    (it as TransactionFormDraft.Standalone).copy(
                        amount = event.amount
                    )
                }
            }

            is TransactionFormEvent.Standalone.SetType -> {
                transactionDraft.update {
                    (it as TransactionFormDraft.Standalone).copy(
                        type = event.type
                    )
                }
            }

            TransactionFormEvent.ToggleIsPosted -> {
                transactionDraft.update {
                    it.withIsPosted(!it.isPosted)
                }
            }

            is TransactionFormEvent.Transfer.SetAccount -> {
                transactionDraft.update {
                    val draft = it as TransactionFormDraft.Transfer

                    when (event.role) {
                        TransactionFormEvent.Transfer.Role.Source -> draft.copy(
                            fromAccount = event.account
                        )

                        TransactionFormEvent.Transfer.Role.Destination -> draft.copy(
                            toAccount = event.account
                        )
                    }
                }
            }

            is TransactionFormEvent.Transfer.SetAmount -> {
                transactionDraft.update {
                    val draft = it as TransactionFormDraft.Transfer

                    when (event.role) {
                        TransactionFormEvent.Transfer.Role.Source -> draft.copy(
                            amountSent = event.amount
                        )

                        TransactionFormEvent.Transfer.Role.Destination -> draft.copy(
                            amountReceived = event.amount
                        )
                    }
                }
            }

            is TransactionFormEvent.SetTransactionFormType -> {
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

internal sealed interface TransactionFormEvent {
    data class SetTitle(val title: String) : TransactionFormEvent
    data class SetCreatedAt(val createdAt: Instant) : TransactionFormEvent
    data object ToggleIsPosted : TransactionFormEvent
    data class SetPostedAt(val postedAt: Instant) : TransactionFormEvent

    data class SetTransactionFormType(val type: com.leeweeder.finlog.ui.transactions.TransactionType) :
        TransactionFormEvent

    data object Save : TransactionFormEvent

    sealed interface Standalone : TransactionFormEvent {
        data class SetAccount(val account: Account) : Standalone
        data class SetType(val type: StandaloneTransactionType) : Standalone
        data class SetAmount(val amount: Amount) : Standalone
    }

    sealed interface Transfer : TransactionFormEvent {
        enum class Role {
            Source,
            Destination
        }

        data class SetAccount(val account: Account, val role: Role) : Transfer
        data class SetAmount(val amount: Amount, val role: Role) : Transfer

    }
}

internal sealed interface TransactionFormDraft {
    val title: String
    val createdAt: Instant
    val isPosted: Boolean
    val postedAt: Instant?

    fun withTitle(title: String): TransactionFormDraft
    fun withCreatedAt(createdAt: Instant): TransactionFormDraft
    fun withIsPosted(isPosted: Boolean): TransactionFormDraft
    fun withPostedAt(postedAt: Instant): TransactionFormDraft

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
    ) : TransactionFormDraft {
        override fun withTitle(title: String): TransactionFormDraft {
            return copy(title = title)
        }

        override fun withCreatedAt(createdAt: Instant): TransactionFormDraft {
            return copy(createdAt = createdAt)
        }

        override fun withIsPosted(isPosted: Boolean): TransactionFormDraft {
            return copy(isPosted = isPosted)
        }

        override fun withPostedAt(postedAt: Instant): TransactionFormDraft {
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
    ) : TransactionFormDraft {
        override fun withTitle(title: String): TransactionFormDraft {
            return copy(title = title)
        }

        override fun withCreatedAt(createdAt: Instant): TransactionFormDraft {
            return copy(createdAt = createdAt)
        }

        override fun withIsPosted(isPosted: Boolean): TransactionFormDraft {
            return copy(isPosted = isPosted)
        }

        override fun withPostedAt(postedAt: Instant): TransactionFormDraft {
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

internal sealed interface TransactionFormUiState {
    data class Success(
        val draft: TransactionFormDraft,
        val availableAccounts: List<Account>,
    ) : TransactionFormUiState

    data object Loading : TransactionFormUiState
    data class Error(var throwable: Throwable) : TransactionFormUiState
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