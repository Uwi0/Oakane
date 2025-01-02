package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import com.kakapo.common.asDouble
import com.kakapo.common.getEndOfMonthUnixTime
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.model.category.CategoryLimitModel
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.datetime.Clock
import kotlin.native.ObjCName

@ObjCName("MonthlyBudgetStateKt")
data class MonthlyBudgetState(
    val id: Long = 0,
    val amount: String = "",
    val expenseCategories: List<CategoryModel> = emptyList(),
    val isEditMode: Boolean = false,
    val dialogShown: Boolean = false,
    val categoryLimits: List<CategoryLimitModel> = emptyList(),
    val selectedCategoryLimit: CategoryLimitModel? = null
){

    val realAmount: Int get() {
        val doubleValue = amount.toDoubleOrNull() ?: 0.0
        return doubleValue.toInt()
    }

    fun asMonthlyBudgetParam(): MonthlyBudgetParam {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        return MonthlyBudgetParam(
            id = id,
            totalBudget = amount.asDouble(),
            spentAmount = 0.0,
            startDate = currentTime,
            endDate = getEndOfMonthUnixTime(),
            createdAt = currentTime,
            updatedAt = currentTime
        )
    }

    fun showDialog(categoryLimit: CategoryLimitModel) = copy(
        selectedCategoryLimit = categoryLimit,
        dialogShown = true
    )

    fun dialog(shown: Boolean): MonthlyBudgetState {
        return if (shown) copy(dialogShown = shown)
        else copy(dialogShown = shown, selectedCategoryLimit = null)
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