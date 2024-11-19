package com.kakapo.oakane.presentation.viewModel.addGoal

import com.kakapo.oakane.model.GoalModel
import kotlinx.datetime.Clock

data class AddGoalState(
    val fileName: String = "",
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

    fun asGoalModel() = GoalModel(
        goalName = goalName,
        amount = targetAmount.toDouble(),
        note = note,
        startDate = startDate,
        endDate = endDate,
        fileName = fileName
    )
}

sealed class AddGoalEffect {
    data object NavigateBack: AddGoalEffect()
    data class ShowError(val message: String): AddGoalEffect()
    data object SuccessSaveGoal: AddGoalEffect()
}

sealed class AddGoalEvent{
    data object NavigateBack: AddGoalEvent()
    data class SetFile(val name: String): AddGoalEvent()
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