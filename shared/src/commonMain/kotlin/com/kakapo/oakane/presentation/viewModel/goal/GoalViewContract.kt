package com.kakapo.oakane.presentation.viewModel.goal

import com.kakapo.oakane.common.daysBetween
import com.kakapo.oakane.common.toFormatIDRCurrency
import com.kakapo.oakane.model.GoalModel

data class GoalState(
    val goal: GoalModel = GoalModel(),
    val savingAmount: String = "",
    val dialogShown: Boolean = false,
    val dialogContent: DialogContent = DialogContent.UpdateAmount
) {

    val dayLeft: Long
        get() {
            return goal.endDate.daysBetween(goal.startDate)
        }

    val savedAmount: String
        get() {
            return goal.savedMoney.toFormatIDRCurrency()
        }

    val targetAmount: String
        get() {
            return goal.amount.toFormatIDRCurrency()
        }

    fun updateDialog(shown: Boolean, content: DialogContent) = copy(
        dialogShown = shown,
        dialogContent = content
    )
}

enum class DialogContent {
    UpdateAmount, DeleteGoal
}

sealed class GoalEffect {
    data object NavigateBack : GoalEffect()
    data class ShowError(val message: String): GoalEffect()
    data class UpdateGoalBy(val id: Long): GoalEffect()
}

sealed class GoalEvent {
    data object NavigateBack : GoalEvent()
    data class Dialog(
        val shown: Boolean,
        val content: DialogContent = DialogContent.UpdateAmount
    ) : GoalEvent()

    data class Change(val amount: String) : GoalEvent()
    data object AddSaving : GoalEvent()
    data object DeleteGoal : GoalEvent()
    data object UpdateGoal : GoalEvent()
}