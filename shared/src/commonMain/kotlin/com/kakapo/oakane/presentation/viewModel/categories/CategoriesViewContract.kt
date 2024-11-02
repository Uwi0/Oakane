package com.kakapo.oakane.presentation.viewModel.categories

import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.model.CategoriesSheetContent

data class CategoriesState(
    val searchQuery: String = "",
    val categories: List<CategoryModel> = emptyList(),
    val filteredCategories: List<CategoryModel> = emptyList(),
    val selectedTab: Int = TransactionType.Expense.ordinal,
    val selectedType: TransactionType = TransactionType.Expense,
    val sheetContent: CategoriesSheetContent = CategoriesSheetContent.Create,
    val showSheet: Boolean = false,
    val categoryName: String = "",
){
    val categoriesColor: List<Int> get() {
        return categories.map { it.formattedColor }
    }

    fun updateCategories(categories: List<CategoryModel>) = copy(
        categories = categories,
        filteredCategories = categories
    )
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
    data class ChangeSheet(val content: CategoriesSheetContent): CategoriesEvent()
}