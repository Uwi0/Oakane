package com.kakapo.oakane.presentation.feature.transaction

sealed interface TransactionUiEvent
data class OnNavigateToEdit(val transactionId: Long): TransactionUiEvent