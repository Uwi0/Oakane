package com.kakapo.oakane.presentation.viewModel.categories

import com.kakapo.oakane.common.toColorInt
import com.kakapo.oakane.model.category.CategoryIconName
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.model.CategoriesSheetContent
import kotlin.native.ObjCName

@ObjCName("CategoriesStateKt")
data class CategoriesState(
    val searchQuery: String = "",
    val categories: List<CategoryModel> = emptyList(),
    val filteredCategories: List<CategoryModel> = emptyList(),
    val selectedTab: Int = TransactionType.Income.ordinal,
    val selectedType: TransactionType = TransactionType.Income,
    val sheetContent: CategoriesSheetContent = CategoriesSheetContent.Create,
    val selectedColor: String = "0xFF4CAF50",
    val selectedIcon: CategoryIconName = CategoryIconName.DEFAULT,
    val fileName: String = "",
    val showSheet: Boolean = false,
    val categoryName: String = "",
    val isEditMode: Boolean = false,
    val categoryId: Long = 0
) {
    val categoriesColor: List<String>
        get() {
            return categories.map { it.color }.distinct()
        }

    val defaultIcon: CategoryIconName
        get() {
            return if (selectedIcon == CategoryIconName.DEFAULT && categories.isNotEmpty()) categories[1].iconName
            else selectedIcon
        }

    val defaultColor: Int
        get() {
            return if (selectedColor == "0xFF4CAF50" && categories.isNotEmpty()) categories[0].formattedColor
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
        fileName = ""
    )

    fun confirmSelectedIcon() = copy(
        sheetContent = CategoriesSheetContent.Create,
    )

    fun updateFileName(fileName: String) = copy(
        fileName = fileName,
        sheetContent = CategoriesSheetContent.Create
    )

    fun updateSheet(visibility: Boolean) = copy(
        showSheet = visibility,
        categoryName = "",
        selectedType = TransactionType.Expense,
        selectedColor = "0xFF4CAF50",
        selectedIcon = CategoryIconName.DEFAULT,
        fileName = "",
        isEditMode = false,
        categoryId = 0
    )

    fun tapped(category: CategoryModel): CategoriesState {
        val fileName = if (category.isDefault) "" else category.icon
        return copy(
            showSheet = true,
            sheetContent = CategoriesSheetContent.Create,
            categoryName = category.name,
            selectedType = category.type,
            selectedColor = category.color,
            selectedIcon = category.iconName,
            fileName = fileName,
            isEditMode = true,
            categoryId = category.id
        )
    }

    fun asCategoryModel(): CategoryModel {
        val icon = fileName.ifEmpty { selectedIcon.displayName }
        val color = selectedColor.ifEmpty { categories[0].color }
        return CategoryModel(
            id = categoryId,
            name = categoryName,
            color = color,
            icon = icon,
            type = selectedType,
            isDefault = fileName.isEmpty()
        )
    }
}

sealed class CategoriesEffect {
    data object NavigateBack: CategoriesEffect()
    data object HideSheet : CategoriesEffect()
}

sealed class CategoriesEvent {
    data object NavigateBack: CategoriesEvent()
    data class Search(val query: String) : CategoriesEvent()
    data class ChangeTab(val index: Int) : CategoriesEvent()
    data class ShowSheet(val visibility: Boolean) : CategoriesEvent()
    data class ChangeCategory(val name: String) : CategoriesEvent()
    data class Selected(val type: TransactionType) : CategoriesEvent()
    data class ChangeSheet(val content: CategoriesSheetContent) : CategoriesEvent()
    data class SelectedColor(val hex: String) : CategoriesEvent()
    data class SelectedIcon(val name: CategoryIconName) : CategoriesEvent()
    data object SaveCategory : CategoriesEvent()
    data object ConfirmIcon : CategoriesEvent()
    data class PickImage(val file: String) : CategoriesEvent()
    data class OnTapped(val category: CategoryModel) : CategoriesEvent()
    data class SwipeToDeleteBy(val id: Long) : CategoriesEvent()
}