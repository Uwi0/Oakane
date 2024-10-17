package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
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
    var selectedDate: Long by mutableLongStateOf(0)
    var selectedCategory: String by mutableStateOf("")

    val typeTile: String
        get() {
            return selectedType?.name ?: "By Type"
        }

    fun onChangedQuery(query: String) {
        searchQuery = query
    }

    fun showBottomSheet(content: TransactionBottomSheet) {
        bottomSheetContent = content
        bottomSheetShown = true
    }

    fun hideBottomSheet() {
        bottomSheetShown = false
    }

    fun changeFilterType(selectedType: TransactionType?) {
        this.selectedType = selectedType
    }

    fun changeFilterDate(selectedDate: Long) {
        this.selectedDate = selectedDate
        bottomSheetShown = false
    }

    fun changeFilterCategory(selectedCategory: String){
        this.selectedCategory = selectedCategory
        bottomSheetShown = false
    }

    fun onFilterByType() {
        if (selectedType == null) {
            showBottomSheet(TransactionBottomSheet.Type)
        } else {
            changeFilterType(null)
        }
    }

    fun onFilterByDate() {
        if (selectedDate == 0L){
            showBottomSheet(TransactionBottomSheet.Date)
        } else {
            changeFilterDate(selectedDate = 0L)
        }
    }

    fun onFilterByCategory(){
        if (selectedCategory == ""){
            showBottomSheet(TransactionBottomSheet.Category)
        } else {
            changeFilterCategory(selectedCategory = "")
        }
    }
}

enum class TransactionBottomSheet {
    Type, Date, Category
}