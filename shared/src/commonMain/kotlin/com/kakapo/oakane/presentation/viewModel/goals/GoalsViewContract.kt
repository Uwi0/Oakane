package com.kakapo.oakane.presentation.viewModel.goals

import com.kakapo.oakane.model.GoalModel

data class GoalsState(
    val searchQuery: String = "",
    val goals: List<GoalModel> = emptyList()
)

sealed class GoalsEffect {
    data object NavigateBack: GoalsEffect()
    data class NavigateToGoal(val id: Long): GoalsEffect()
}

sealed class GoalsEvent {
    data object NavigateBack: GoalsEvent()
    data class FilterBy(val query: String): GoalsEvent()
    data class NavigateToGoal(val id: Long): GoalsEvent()
}
