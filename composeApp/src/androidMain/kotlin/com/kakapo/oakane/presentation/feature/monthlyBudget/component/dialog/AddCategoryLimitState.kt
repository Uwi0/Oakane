package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.kakapo.common.asRealCurrencyValue
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.toFormatNumber
import kotlinx.coroutines.delay

@Composable
fun rememberAddCategoryLimitState(
    expenseCategories: List<CategoryModel>,
    categoryLimit: CategoryLimitModel?,
    currency: Currency
) = remember {
    AddCategoryLimitState(expenseCategories, categoryLimit, currency)
}

class AddCategoryLimitState(
    expenseCategories: List<CategoryModel> = emptyList(),
    categoryLimit: CategoryLimitModel? = null,
    val currency: Currency
) {

    var limitAmount: String by mutableStateOf("")
    var selectedOptionText: String by mutableStateOf("")
    var expanded: Boolean by mutableStateOf(false)
    var selectedCategory: CategoryModel by mutableStateOf(CategoryModel())
    var filteredOptions = emptyList<CategoryModel>()

    val categoryId get() = selectedCategory.id
    val formattedAmount get() = limitAmount.asRealCurrencyValue()

    private var expenseCategory = expenseCategories
    private var defaultCategory = expenseCategories.first()
    private var userStartedTyping: Boolean by mutableStateOf(false)

    init {
        if (categoryLimit == null) {
            selectedCategory = defaultCategory
            filteredOptions = expenseCategory
        } else {
            limitAmount = categoryLimit.limit.toFormatNumber(currency)
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