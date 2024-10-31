package com.kakapo.oakane.presentation.viewModel.categories

import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType

data class CategoriesUiState(
    val searchQuery: String = "",
    val categories: List<CategoryModel> = emptyList(),
    val filteredCategories: List<CategoryModel> = emptyList(),
    val selectedTab: Int = TransactionType.Expense.ordinal,
    val isSheetShown: Boolean = false
)

sealed class CategoriesEvent {
    data class Search(val query: String) : CategoriesEvent()
    data class ChangeTab(val index: Int) : CategoriesEvent()
}