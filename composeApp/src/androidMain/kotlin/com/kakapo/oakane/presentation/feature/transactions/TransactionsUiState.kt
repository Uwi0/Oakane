package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
internal fun rememberTransactionUiState() = remember {
    TransactionsUiState()
}

class TransactionsUiState {
    var searchQuery by mutableStateOf("")

    fun onChangedQuery(query: String){
        searchQuery = query
    }
}