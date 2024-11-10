package com.kakapo.oakane.presentation.feature.transactions

import com.kakapo.oakane.model.transaction.TransactionModel

sealed interface TransactionsUiEvent
data object OnNavigateBack : TransactionsUiEvent
data object OnDismissBottomSheet : TransactionsUiEvent
data class OnDelete(val item: TransactionModel) : TransactionsUiEvent
data class OnNavigateToTransaction(val id: Long) : TransactionsUiEvent