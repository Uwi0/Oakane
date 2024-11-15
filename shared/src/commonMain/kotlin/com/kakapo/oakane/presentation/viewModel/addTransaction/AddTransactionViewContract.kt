package com.kakapo.oakane.presentation.viewModel.addTransaction

import com.kakapo.oakane.common.asDouble
import com.kakapo.oakane.data.model.TransactionParam
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.model.transaction.TransactionType
import kotlinx.datetime.Clock

data class AddTransactionState(
    val transactionId: Long = 0,
    val title: String = "",
    val amount: String = "0",
    val transactionType: TransactionType = TransactionType.Income,
    val category: CategoryModel = CategoryModel(),
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val note: String = "",
    val isDropdownExpanded: Boolean = false,
    val isShowDialog: Boolean = false,
    val sheetShown: Boolean = false,
    val categories: List<CategoryModel> = emptyList()
) {
    val isEditMode get() = transactionId != 0L

    fun dropDownType(expanded: Boolean) = copy(isDropdownExpanded = expanded)

    fun update(category: CategoryModel) = copy(
        category = category,
        sheetShown = false
    )

    fun update(date: Long) = copy(
        date = date,
        isShowDialog = false
    )

    fun copy(transaction: TransactionModel) = copy(
        transactionId = transaction.id,
        title = transaction.title,
        amount = transaction.amount.toString(),
        transactionType = transaction.type,
        category = transaction.category,
    )

    fun asTransactionParam() = TransactionParam(
        title = title,
        amount = amount.asDouble(),
        type = transactionType.ordinal.toLong(),
        category = category,
        dateCreated = date,
        note = note
    )
}

sealed class AddTransactionEffect {
    data object NavigateBack : AddTransactionEffect()
}

sealed class AddTransactionEvent {
    data object NavigateBack : AddTransactionEvent()
    data class ChangedTitle(val value: String) : AddTransactionEvent()
    data class ChangedAmount(val value: String) : AddTransactionEvent()
    data class ChangeNote(val value: String) : AddTransactionEvent()
    data class ChangeDate(val value: Long) : AddTransactionEvent()
    data class DropDownTypeIs(val expanded: Boolean) : AddTransactionEvent()
    data class ChangeType(val value: TransactionType) : AddTransactionEvent()
    data class Dialog(val shown: Boolean) : AddTransactionEvent()
    data class Sheet(val shown: Boolean) : AddTransactionEvent()
    data object SaveTransaction : AddTransactionEvent()
    data class SetCategory(val value: CategoryModel) : AddTransactionEvent()
}