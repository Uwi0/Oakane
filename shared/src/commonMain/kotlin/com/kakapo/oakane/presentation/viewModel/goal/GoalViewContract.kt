package com.kakapo.oakane.presentation.viewModel.goal

import com.kakapo.oakane.common.daysBetween
import com.kakapo.oakane.model.GoalModel

data class GoalState(
    val goal: GoalModel = GoalModel(),
    val savingAmount: String = "",
    val dialogShown: Boolean = false
){
    val progress: Float get() {
        return (goal.savedMoney / goal.amount).toFloat()
    }

    val dayLeft: Long get() {
        return goal.endDate.daysBetween(goal.startDate)
    }
}

sealed class GoalEffect {
    data object NavigateBack: GoalEffect()
}

sealed class GoalEvent {
    data object NavigateBack: GoalEvent()
    data class Dialog(val shown: Boolean): GoalEvent()
    data class Change(val amount: String): GoalEvent()
    data object AddSaving: GoalEvent()
}