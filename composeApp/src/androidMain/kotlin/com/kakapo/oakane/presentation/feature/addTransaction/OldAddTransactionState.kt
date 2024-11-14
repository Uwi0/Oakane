package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.oakane.model.transaction.TransactionType

@Composable
fun rememberAddTransactionState(
    transactionId: Long,
    categories: List<String>
) = remember {
    OldAddTransactionState(
        categories = categories
    )
}

class OldAddTransactionState(
    val categories: List<String>
) {

    var title by mutableStateOf("")

    var note by mutableStateOf("")

    var amount by mutableStateOf("")

    var isTypeExpanded by mutableStateOf(false)
    var selectedTransactionType by mutableStateOf(TransactionType.Expense.name)
    val transactionOptions = TransactionType.entries.map { it.name }

    var isCategoryExpanded by mutableStateOf(false)
    var selectedCategory by mutableStateOf(categories.first())

    var isDatePickerDialogShown by mutableStateOf(false)
    var selectedDate by mutableLongStateOf(System.currentTimeMillis())

    fun onTitleChanged(title: String) {
        this.title = title
    }

    fun onNoteChanged(note: String) {
        this.note = note
    }

    fun onAmountChanged(amount: String) {
        if (amount.all { it.isDigit() }) {
            this.amount = amount
        }
    }

    fun toggleTransactionType(isExpanded: Boolean) {
        this.isTypeExpanded = isExpanded
    }

    fun onTransactionTypeChanged(transactionType: String) {
        this.isTypeExpanded = false
        selectedTransactionType = transactionType
    }

    fun toggleCategoryExpanded(isExpanded: Boolean) {
        this.isCategoryExpanded = isExpanded
    }

    fun onCategoryChanged(category: String) {
        this.isCategoryExpanded = false
        selectedCategory = category
    }

    fun onSelectedDate(date: Long) {
        this.selectedDate = date
        this.isDatePickerDialogShown = false
    }

    fun toggleDatePickerDialog(isShown: Boolean) {
        this.isDatePickerDialogShown = isShown
    }

}

val dummyCategories = listOf(
    "Food & Dining",
    "Transportation",
    "Entertainment",
    "Utilities",
    "Health & Fitness"
)