package com.kakapo.oakane.presentation.viewModel.goal

import com.kakapo.common.daysBetween
import com.kakapo.common.toFormatIDRWithCurrency
import com.kakapo.model.GoalModel
import kotlin.native.ObjCName

@ObjCName("GoalStateKt")
data class GoalState(
    val goal: GoalModel = GoalModel(),
    val savingAmount: String = "",
    val dialogShown: Boolean = false,
    val dialogContent: GoalDialogContent = GoalDialogContent.UpdateAmount
) {

    val dayLeft: Long
        get() {
            return goal.endDate.daysBetween(goal.startDate)
        }

    val savedAmount: String
        get() {
            return goal.savedMoney.toFormatIDRWithCurrency()
        }

    val targetAmount: String
        get() {
            return goal.amount.toFormatIDRWithCurrency()
        }

    fun updateDialog(shown: Boolean, content: GoalDialogContent) = copy(
        dialogShown = shown,
        dialogContent = content
    )
}

enum class GoalDialogContent {
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
        val content: GoalDialogContent = GoalDialogContent.UpdateAmount
    ) : GoalEvent()

    data class Change(val amount: String) : GoalEvent()
    data object AddSaving : GoalEvent()
    data object DeleteGoal : GoalEvent()
    data object UpdateGoal : GoalEvent()
}