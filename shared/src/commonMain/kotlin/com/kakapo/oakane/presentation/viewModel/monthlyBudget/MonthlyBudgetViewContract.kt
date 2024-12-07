package com.kakapo.oakane.presentation.viewModel.monthlyBudget

import com.kakapo.oakane.common.asDouble
import com.kakapo.oakane.common.getEndOfMonthUnixTime
import com.kakapo.oakane.data.model.MonthlyBudgetParam
import com.kakapo.oakane.model.category.CategoryLimitModel
import com.kakapo.oakane.model.category.CategoryModel
import kotlinx.datetime.Clock

data class MonthlyBudgetState(
    val id: Long = 0,
    val amount: String = "",
    val expenseCategories: List<CategoryModel> = emptyList(),
    val isEditMode: Boolean = false,
    val dialogShown: Boolean = false,
    val categoryLimits: List<CategoryLimitModel> = emptyList()
){
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
    data object Save: MonthlyBudgetEvent()
}