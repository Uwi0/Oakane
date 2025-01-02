package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.asDouble
import com.kakapo.oakane.model.category.CategoryLimitModel
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.coroutines.delay

@Composable
fun rememberAddCategoryLimitState(
    expenseCategories: List<CategoryModel>,
    categoryLimit: CategoryLimitModel?
) = remember {
    AddCategoryLimitState(expenseCategories, categoryLimit)
}

class AddCategoryLimitState(
    expenseCategories: List<CategoryModel> = emptyList(),
    categoryLimit: CategoryLimitModel? = null
) {

    var limitAmount: String by mutableStateOf("")
    var selectedOptionText: String by mutableStateOf("")
    var expanded: Boolean by mutableStateOf(false)
    var selectedCategory: CategoryModel by mutableStateOf(CategoryModel())
    var filteredOptions = emptyList<CategoryModel>()

    val categoryId get() = selectedCategory.id
    val formattedAmount get() = limitAmount.asDouble()

    private var expenseCategory = expenseCategories
    private var defaultCategory = expenseCategories.first()
    private var userStartedTyping: Boolean by mutableStateOf(false)

    init {
        if (categoryLimit == null) {
            selectedCategory = defaultCategory
            filteredOptions = expenseCategory
        } else {
            limitAmount = categoryLimit.limit.toString()
            selectedCategory = categoryLimit.category
            filteredOptions = listOf(categoryLimit.category)
        }

    }

    fun changedLimitAmount(amount: String) {
        limitAmount = amount
    }

    fun changeSelectedOptionText(text: String) {
        selectedOptionText = text
        userStartedTyping = true
    }

    suspend fun filterCategories(query: String) {
        if (userStartedTyping) {
            delay(500)
            filteredOptions = expenseCategory.filter { it.name.contains(query, ignoreCase = true) }
            expanded = true
        }
    }

    fun changeExpanded(expanded: Boolean) {
        this.expanded = expanded
    }

    fun onClickedCategory(category: CategoryModel) {
        selectedCategory = category
        selectedOptionText = category.name
        expanded = false
        userStartedTyping = false
    }
}