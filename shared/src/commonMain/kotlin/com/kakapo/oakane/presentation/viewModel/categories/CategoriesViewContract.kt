package com.kakapo.oakane.presentation.viewModel.categories

import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType

data class CategoriesUiState(
    val searchQuery: String = "",
    val categories: List<CategoryModel> = emptyList(),
    val filteredCategories: List<CategoryModel> = emptyList(),
    val selectedTab: Int = TransactionType.Expense.ordinal,
    val selectedType: TransactionType = TransactionType.Expense,
    val showSheet: Boolean = false,
    val categoryName: String = "",
){
    val categoriesColor: List<Int> get() {
        return categories.map { it.formattedColor }
    }
}

sealed class CategoriesEffect {
    data object HideSheet : CategoriesEffect()
}

sealed class CategoriesEvent {
    data class Search(val query: String) : CategoriesEvent()
    data class ChangeTab(val index: Int) : CategoriesEvent()
    data class ShowSheet(val visibility: Boolean) : CategoriesEvent()
    data class ChangeCategory(val name: String): CategoriesEvent()
    data class Selected(val type: TransactionType): CategoriesEvent()
}