package com.kakapo.oakane.presentation.viewModel.goals

import com.kakapo.model.goal.GoalModel
import com.kakapo.model.system.Theme
import kotlin.native.ObjCName

@ObjCName("GoalsStateKt")
data class GoalsState(
    val searchQuery: String = "",
    val goals: List<GoalModel> = emptyList(),
    val filteredGoals: List<GoalModel> = emptyList(),
    val theme: Theme = Theme.System,
    val showDrawer: Boolean = false
)

sealed class GoalsEffect {
    data class ShowError(val message: String): GoalsEffect()
    data object NavigateBack: GoalsEffect()
    data class NavigateToGoal(val id: Long): GoalsEffect()
    data object AddGoal: GoalsEffect()
    data object OpenDrawer: GoalsEffect()
}

sealed class GoalsEvent {
    data object NavigateBack: GoalsEvent()
    data class FilterBy(val query: String): GoalsEvent()
    data class NavigateToGoal(val id: Long): GoalsEvent()
    data object AddGoal: GoalsEvent()
    data object OpenDrawer: GoalsEvent()
}
