package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.model.transaction.asLong

@Composable
fun rememberAddTransactionState(
    transactionId: Long,
    categories: List<String>
) = remember {
    AddTransactionState(
        transactionId = transactionId,
        categories = categories
    )
}

class AddTransactionState(
    val transactionId: Long,
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

    val screenTitle: String
        get() {
            return if (transactionId == 0L) {
                "Add Transaction"
            } else {
                "Edit Transaction"
            }
        }

    val buttonTitle: String
        get() {
            return if (transactionId == 0L) {
                "Add Transaction"
            } else {
                "Save Transaction"
            }
        }

    fun initializeData(transaction: TransactionModel) {
        title = transaction.title
        note = transaction.note
        amount = transaction.amount.toString()
        selectedTransactionType = transaction.type.name
        selectedCategory = transaction.category
    }

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

    fun getTransaction() = TransactionParam(
        title = title,
        amount = amount.toDouble(),
        type = selectedTransactionType.asLong(),
        category = selectedCategory,
        dateCreated = selectedDate,
        note = note
    )
}

val dummyCategories = listOf(
    "Food & Dining",
    "Transportation",
    "Entertainment",
    "Utilities",
    "Health & Fitness"
)