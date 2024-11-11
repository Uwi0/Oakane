package com.kakapo.oakane.presentation.viewModel.home

import com.kakapo.oakane.model.GoalModel
import com.kakapo.oakane.model.dummyGoals
import com.kakapo.oakane.model.transaction.TransactionModel

data class HomeState(
    val transactions: List<TransactionModel> = emptyList(),
    val goals: List<GoalModel> = dummyGoals()
)

sealed class HomeEffect {
    data object OpenDrawer: HomeEffect()
    data object ToCreateTransaction: HomeEffect()
    data object ToTransactions: HomeEffect()
}

sealed class HomeEvent {
    data object OpenDrawer: HomeEvent()
    data object ToCreateTransaction: HomeEvent()
    data object ToTransactions: HomeEvent()
}