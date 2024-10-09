package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.oakane.model.TransactionType

@Composable
fun rememberAddTransactionState(categories: List<String>) = remember {
    AddTransactionState(categories = categories)
}

class AddTransactionState(val categories: List<String>) {

    var title by mutableStateOf("")

    var note by mutableStateOf("")

    var amount by mutableStateOf("")

    var isTypeExpanded by mutableStateOf(false)
    var selectedTransactionType by mutableStateOf(TransactionType.Expense.asString())
    val transactionOptions = TransactionType.entries.map { it.asString() }

    var isCategoryExpanded by mutableStateOf(false)
    var selectedCategory by mutableStateOf(categories.first())

    fun onTitleChanged(title: String) {
        this.title = title
    }

    fun onNoteChanged(note: String) {
        this.note = note
    }

    fun onAmountChanged(amount: String) {
        if(amount.all { it.isDigit() }) {
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

    private fun TransactionType.asString(): String {
        return when(this) {
            TransactionType.Income -> "Income"
            TransactionType.Expense -> "Expense"
        }
    }
}

val dummyCategories = listOf(
    "Food & Dining",
    "Transportation",
    "Entertainment",
    "Utilities",
    "Health & Fitness"
)