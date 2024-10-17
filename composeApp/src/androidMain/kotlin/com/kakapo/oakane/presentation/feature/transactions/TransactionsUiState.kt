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
    var bottomSheetShown by mutableStateOf(false)
    var bottomSheetContent: TransactionBottomSheet by mutableStateOf(TransactionBottomSheet.Date)

    fun onChangedQuery(query: String){
        searchQuery = query
    }

    fun showBottomSheet(content: TransactionBottomSheet) {
        bottomSheetContent = content
        bottomSheetShown = true
    }

    fun hideBottomSheet(){
        bottomSheetShown = false
    }

}

enum class TransactionBottomSheet {
    Type, Date, Category
}