package com.kakapo.oakane.presentation.viewModel.addGoal

import com.kakapo.model.GoalModel
import kotlinx.datetime.Clock
import kotlin.native.ObjCName

@ObjCName("AddGoalStateKt")
data class AddGoalState(
    val id: Long = 0L,
    val fileName: String = "",
    val goalName: String = "",
    val targetAmount: String = "",
    val note: String = "",
    val startDate: Long = Clock.System.now().toEpochMilliseconds(),
    val endDate: Long = Clock.System.now().toEpochMilliseconds(),
    val dialogShown: Boolean = false,
    val dialogContent: GoalDateContent = GoalDateContent.Start
){

    val isEditMode: Boolean get() = id != 0L

    val initialDateDialog: Long get() {
        return when(dialogContent){
            GoalDateContent.Start -> startDate
            GoalDateContent.End -> endDate
        }
    }

    val amount: Int get() {
        val doubleValue = targetAmount.toDoubleOrNull() ?: 0.0
        return doubleValue.toInt()
    }

    fun updateDialog(event: AddGoalEvent.ShowDialog) = copy(
        dialogShown = true,
        dialogContent = event.date
    )

    fun updateStart(date: Long) = copy(startDate = date, dialogShown = false)

    fun updateEnd(date: Long) = copy(endDate = date, dialogShown = false)

    fun update(goal: GoalModel) = copy(
        fileName = goal.fileName,
        goalName = goal.goalName,
        targetAmount = goal.amount.toString(),
        note = goal.note,
        startDate = goal.startDate,
        endDate = goal.endDate
    )

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