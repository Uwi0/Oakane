package com.kakapo.oakane.presentation.viewModel.addGoal

import kotlinx.datetime.Clock

data class AddGoalState(
    val goalName: String = "",
    val targetAmount: String = "",
    val note: String = "",
    val startDate: Long = Clock.System.now().toEpochMilliseconds(),
    val endDate: Long = Clock.System.now().toEpochMilliseconds(),
    val dialogShown: Boolean = false,
    val dialogContent: GoalDateContent = GoalDateContent.Start
){

    val initialDateDialog: Long get() {
        return when(dialogContent){
            GoalDateContent.Start -> startDate
            GoalDateContent.End -> endDate
        }
    }

    fun updateDialog(event: AddGoalEvent.ShowDialog) = copy(
        dialogShown = true,
        dialogContent = event.date
    )

    fun updateStart(date: Long) = copy(startDate = date, dialogShown = false)

    fun updateEnd(date: Long) = copy(endDate = date, dialogShown = false)

}

sealed class AddGoalEvent{
    data class SetName(val value: String): AddGoalEvent()
    data class SetTarget(val amount: String): AddGoalEvent()
    data class SetNote(val value: String): AddGoalEvent()
    data class SetStart(val date: Long): AddGoalEvent()
    data class SetEnd(val date: Long): AddGoalEvent()
    data class ShowDialog(val date: GoalDateContent): AddGoalEvent()
    data object HideDialog: AddGoalEvent()
    data object SaveGoal: AddGoalEvent()
}

enum class GoalDateContent{
    Start, End
}