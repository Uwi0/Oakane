package com.kakapo.oakane.presentation.viewModel.categories

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.category.CategoryIconName
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
    val selectedColor: String = "",
    val selectedIcon: CategoryIconName = CategoryIconName.DEFAULT,
    val showSheet: Boolean = false,
    val categoryName: String = "",
){
    val categoriesColor: List<String> get() {
        return categories.map { it.color }.distinct()
    }

    val defaultSelectedColor: Int get() {
        return if(selectedColor == "") categories[0].formattedColor
        else selectedColor.toColorInt()
    }

    fun updateCategories(categories: List<CategoryModel>) = copy(
        categories = categories,
        filteredCategories = categories
    )

    fun updateColor(hex: String) = copy(
        selectedColor = hex,
        sheetContent = CategoriesSheetContent.Create
    )

    fun updateIcon(name: CategoryIconName) = copy(
        selectedIcon = name,
        sheetContent = CategoriesSheetContent.Create
    )

    fun updateSheet(visibility: Boolean) = copy(
        showSheet = visibility,
        categoryName = "",
        selectedType = TransactionType.Expense,
        selectedColor = "",
        selectedIcon = CategoryIconName.DEFAULT
    )

    fun asCategoryModel(): CategoryModel {
        return CategoryModel(
            name = categoryName,
            color = selectedColor,
            icon = selectedIcon.displayName,
            type = selectedType,
            isDefault = true
        )
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
    data class ChangeSheet(val content: CategoriesSheetContent): CategoriesEvent()
    data class SelectedColor(val hex: String): CategoriesEvent()
    data class SelectedIcon(val name: CategoryIconName): CategoriesEvent()
    data object SaveCategory : CategoriesEvent()
}