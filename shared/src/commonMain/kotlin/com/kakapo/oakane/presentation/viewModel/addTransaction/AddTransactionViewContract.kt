package com.kakapo.oakane.presentation.viewModel.addTransaction

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.data.model.TransactionParam
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatNumber
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import com.kakapo.model.wallet.WalletModel
import kotlinx.datetime.Clock

data class AddTransactionState(
    val transactionId: Long = 0,
    val title: String = "",
    val transactionAmount: String = "",
    val transactionAmountUpdate: String = "",
    val transactionType: TransactionType = TransactionType.Income,
    val category: CategoryModel = CategoryModel(),
    val date: Long = Clock.System.now().toEpochMilliseconds(),
    val note: String = "",
    val isDropdownExpanded: Boolean = false,
    val isShowDialog: Boolean = false,
    val sheetShown: Boolean = false,
    val categories: List<CategoryModel> = emptyList(),
    val currency: Currency = Currency.IDR,
    val theme: Theme = Theme.System,
    val imageFileName: String = "",
    val titleFieldError: Boolean = false,
    val amountFieldError: Boolean = false,
    val wallets: List<WalletModel> = emptyList(),
    val selectedWallet: WalletModel = WalletModel(),
    val excludeFromBudget: Boolean = false
) {
    val isEditMode get() = transactionId != 0L

    val amount: Int
        get() {
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

    fun copy(transaction: TransactionModel, wallet: WalletModel) = copy(
        transactionId = transaction.id,
        title = transaction.title,
        transactionAmount = transaction.amount.toFormatNumber(currency),
        transactionAmountUpdate = transaction.amount.toFormatNumber(currency),
        transactionType = transaction.type,
        category = transaction.category,
        note = transaction.note,
        imageFileName = transaction.imageFileName,
        selectedWallet = wallet,
        excludeFromBudget = transaction.excludedBudget
    )

    fun asTransactionParam() = TransactionParam(
        id = transactionId,
        title = title,
        amount = transactionAmount.asRealCurrencyValue(),
        type = transactionType.ordinal.toLong(),
        category = category,
        dateCreated = date,
        walletId = selectedWallet.id,
        note = note,
        imageFile = imageFileName,
        excludeFromBudget = excludeFromBudget
    )

    companion object {
        fun default() = AddTransactionState()
    }
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
    data class ChangeWallet(val wallet: WalletModel) : AddTransactionEvent()
    data class ExcludeFromBudget(val value: Boolean) : AddTransactionEvent()
}