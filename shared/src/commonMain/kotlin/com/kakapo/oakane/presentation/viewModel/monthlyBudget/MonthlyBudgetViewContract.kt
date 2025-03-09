package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import com.kakapo.common.asRealCurrencyValue
import com.kakapo.common.getEndOfMonthUnixTime
import com.kakapo.data.model.MonthlyBudgetParam
import com.kakapo.data.model.MonthlyBudgetRecurring
import com.kakapo.data.model.toCategoryLimitParamEncodedString
import com.kakapo.model.Currency
import com.kakapo.model.category.CategoryLimitModel
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.monthlyBudget.MonthlyBudgetModel
import com.kakapo.model.system.Theme
import com.kakapo.model.toFormatNumber
import kotlinx.datetime.Clock

data class MonthlyBudgetState(
    val id: Long = 0,
    val amount: String = "",
    val amountUpdated: String = "",
    val expenseCategories: List<CategoryModel> = emptyList(),
    val isEditMode: Boolean = false,
    val dialogShown: Boolean = false,
    val categoryLimits: List<CategoryLimitModel> = emptyList(),
    val selectedCategoryLimit: CategoryLimitModel? = null,
    val currency: Currency = Currency.IDR,
    val theme: Theme = Theme.System
){

    val realAmount: Int get() {
        val doubleValue = amount.toDoubleOrNull() ?: 0.0
        return doubleValue.toInt()
    }

    fun copy(budget: MonthlyBudgetModel) = copy(
        id = budget.id,
        amount = budget.totalBudget.toFormatNumber(budget.currency),
        amountUpdated = budget.totalBudget.toFormatNumber(budget.currency),
        currency = budget.currency
    )

    fun asMonthlyBudgetParam(): MonthlyBudgetParam {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return MonthlyBudgetParam(
            id = id,
            totalBudget = amount.asRealCurrencyValue(),
            spentAmount = 0.0,
            startDate = currentTime,
            endDate = getEndOfMonthUnixTime(),
            createdAt = currentTime,
            updatedAt = currentTime
        )
    }

    fun asMonthlyBudgetReCurring(): String {
        val value =  MonthlyBudgetRecurring(amount = amount.asRealCurrencyValue())
        return value.toEncodeString()
    }

    fun asCategoryLimitRecurring(): String {
        return categoryLimits.toCategoryLimitParamEncodedString()
    }

    fun showDialog(categoryLimit: CategoryLimitModel) = copy(
        selectedCategoryLimit = categoryLimit,
        dialogShown = true
    )

    fun dialog(shown: Boolean): MonthlyBudgetState {
        return if (shown) copy(dialogShown = shown)
        else copy(dialogShown = shown, selectedCategoryLimit = null)
    }

    companion object {
        fun default() = MonthlyBudgetState()
    }
}

sealed class MonthlyBudgetEffect {
    data object NavigateBack: MonthlyBudgetEffect()
    data class ShowError(val message: String): MonthlyBudgetEffect()
}

typealias CreateCategoryLimit = MonthlyBudgetEvent.CreateCategoryLimitBy

sealed class MonthlyBudgetEvent {
    data object NavigateBack: MonthlyBudgetEvent()
    data class Changed(val amount: String): MonthlyBudgetEvent()
    data class Dialog(val shown: Boolean): MonthlyBudgetEvent()
    data class CreateCategoryLimitBy(val categoryId: Long, val limitAmount: Double): MonthlyBudgetEvent()
    data class Selected(val categoryLimit: CategoryLimitModel): MonthlyBudgetEvent()
    data object Save: MonthlyBudgetEvent()
}