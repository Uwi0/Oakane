package com.kakapo.oakane.presentation.viewModel.home

import com.kakapo.oakane.model.GoalModel
import com.kakapo.oakane.model.transaction.TransactionModel

data class HomeState(
    val transactions: List<TransactionModel> = emptyList(),
    val goals: List<GoalModel> = emptyList()
)

sealed class HomeEffect {
    data class ShowError(val message: String): HomeEffect()
    data object OpenDrawer: HomeEffect()
    data object ToCreateTransaction: HomeEffect()
    data object ToTransactions: HomeEffect()
    data object ToCreateGoal: HomeEffect()
    data class ToGoalWith(val id: Long): HomeEffect()
}

sealed class HomeEvent {
    data object OpenDrawer: HomeEvent()
    data object ToCreateTransaction: HomeEvent()
    data object ToTransactions: HomeEvent()
    data object ToCreateGoal: HomeEvent()
    data class ToGoalWith(val id: Long): HomeEvent()
}