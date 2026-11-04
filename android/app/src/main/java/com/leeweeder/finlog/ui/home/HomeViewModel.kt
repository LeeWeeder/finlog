package com.leeweeder.finlog.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leeweeder.finlog.domain.model.account.AccountWithBalance
import com.leeweeder.finlog.domain.use_case.GetActiveAccountsWithBalanceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    getActiveAccountsWithBalanceUseCase: GetActiveAccountsWithBalanceUseCase
) : ViewModel() {
    val uiState = getActiveAccountsWithBalanceUseCase.invoke().map {
        HomeUiState.Success(it)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeUiState.Loading
    )
}

internal sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val accountsWithBalance: List<AccountWithBalance>
    ) : HomeUiState
}