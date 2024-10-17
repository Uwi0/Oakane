package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.oakane.model.transaction.TransactionType

@Composable
internal fun rememberTransactionUiState() = remember {
    TransactionsUiState()
}

class TransactionsUiState {
    var searchQuery by mutableStateOf("")
    var bottomSheetShown by mutableStateOf(false)
    var bottomSheetContent: TransactionBottomSheet by mutableStateOf(TransactionBottomSheet.Date)
    var selectedType: TransactionType? by mutableStateOf(null)

    val typeTile: String get() {
        return selectedType?.name ?: "By Type"
    }

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

    fun changeFilterType(selectedType: TransactionType?){
        this.selectedType = selectedType
    }

    fun onFilterTypeClicked(){
        if (selectedType == null){
            showBottomSheet(TransactionBottomSheet.Type)
        } else {
            changeFilterType(null)
        }
    }

}

enum class TransactionBottomSheet {
    Type, Date, Category
}