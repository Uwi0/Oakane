package com.kakapo.oakane.presentation.viewModel.addTransaction

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.data.model.TransactionParam
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import kotlinx.datetime.Clock
import kotlin.native.ObjCName

@ObjCName("AddTransactionStateKt")
data class AddTransactionState(
    val transactionId: Long = 0,
    val title: String = "",
    val transactionAmount: String = "0",
    val transactionType: TransactionType = TransactionType.Income,
    val category: CategoryModel = CategoryModel(),
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val note: String = "",
    val isDropdownExpanded: Boolean = false,
    val isShowDialog: Boolean = false,
    val sheetShown: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val currency: Currency = Currency.IDR,
    val imageFileName: String = ""
) {
    val isEditMode get() = transactionId != 0L

    val amount: Int get() {
        val doubleValue = transactionAmount.toDoubleOrNull() ?: 0.0
        return doubleValue.toInt()
    }

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
        transactionAmount = transaction.amount.toInt().toString(),
        transactionType = transaction.type,
        category = transaction.category,
        note = transaction.note,
        imageFileName = transaction.imageFileName
    )

    fun asTransactionParam() = TransactionParam(
        id = transactionId,
        title = title,
        amount = transactionAmount.asRealCurrencyValue(),
        type = transactionType.ordinal.toLong(),
        category = category,
        dateCreated = date,
        note = note,
        imageFile = imageFileName
    )
}

sealed class AddTransactionEffect {
    data object NavigateBack : AddTransactionEffect()
    data class ShowError(val message: String) : AddTransactionEffect()
    data object TakePhoto : AddTransactionEffect()
    data object PickImage : AddTransactionEffect()
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
    data object TakePhoto : AddTransactionEvent()
    data object PickImage : AddTransactionEvent()
    data class SaveImageFile(val name: String) : AddTransactionEvent()
    data object ClearImage : AddTransactionEvent()
}