package com.kakapo.oakane.presentation.viewModel.transactions

import com.kakapo.model.category.CategoryModel
import com.kakapo.model.system.Theme
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.transaction.TransactionType
import kotlin.native.ObjCName

@ObjCName("TransactionsStateKt")
data class TransactionsState(
    val transactions: List<TransactionModel> = emptyList(),
    val filteredTransactions: List<TransactionModel> = transactions,
    val searchQuery: String = "",
    val selectedType: TransactionType? = null,
    val selectedDate: Long = 0L,
    val selectedCategory: CategoryModel? = null,
    val sheetShown: Boolean = false,
    val showDrawer: Boolean = false,
    val sheetContent: TransactionsContentSheet = TransactionsContentSheet.Type,
    val theme: Theme = Theme.System
) {
    val typeTile: String
        get() {
            return selectedType?.name ?: "By Type"
        }

    val categories: List<CategoryModel> get() = transactions.map { it.category }

    fun showSheet(content: TransactionsContentSheet) = copy(
        sheetShown = true,
        sheetContent = content
    )

    fun hideSheet() = copy(
        sheetShown = false,
        sheetContent = TransactionsContentSheet.Type
    )
}

sealed class TransactionsEffect{
    data object NavigateBack: TransactionsEffect()
    data object HideSheet: TransactionsEffect()
    data class ToDetail(val id: Long): TransactionsEffect()
    data class ShowError(val message: String): TransactionsEffect()
    data object OpenDrawer: TransactionsEffect()
}

sealed class TransactionsEvent {
    data object NavigateBack: TransactionsEvent()
    data class FilterBy(val query: String) : TransactionsEvent()
    data class FilterByType(val value: TransactionType?) : TransactionsEvent()
    data class FilterByDate(val timeMillis: Long) : TransactionsEvent()
    data class FilterByCategory(val value: CategoryModel?) : TransactionsEvent()
    data class Delete(val transaction: TransactionModel): TransactionsEvent()
    data class ShowSheet(val content: TransactionsContentSheet): TransactionsEvent()
    data object HideSheet: TransactionsEvent()
    data class ToDetail(val id: Long): TransactionsEvent()
    data object OpenDrawer: TransactionsEvent()
}

enum class TransactionsContentSheet {
    Type, Date, Category
}