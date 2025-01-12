package com.kakapo.oakane.presentation.viewModel.home

import com.kakapo.model.GoalModel
import com.kakapo.model.monthlyBudget.MonthlyBudgetOverView
import com.kakapo.model.transaction.TransactionModel
import com.kakapo.model.wallet.WalletModel
import kotlin.native.ObjCName

@ObjCName("HomeStateKt")
data class HomeState(
    val wallet: WalletModel = WalletModel(),
    val transactions: List<TransactionModel> = emptyList(),
    val goals: List<GoalModel> = emptyList(),
    val monthlyBudgetOverView: MonthlyBudgetOverView = MonthlyBudgetOverView(),
)

sealed class HomeEffect {
    data class ShowError(val message: String): HomeEffect()
    data object OpenDrawer: HomeEffect()
    data object ToCreateTransaction: HomeEffect()
    data object ToTransactions: HomeEffect()
    data class ToTransaction(val id: Long): HomeEffect()
    data object ToCreateGoal: HomeEffect()
    data object ToGoals: HomeEffect()
    data class ToGoalWith(val id: Long): HomeEffect()
    data object ToMonthlyBudget: HomeEffect()
    data object ToWallets: HomeEffect()
}

sealed class HomeEvent {
    data object OpenDrawer: HomeEvent()
    data object ToCreateTransaction: HomeEvent()
    data object ToTransactions: HomeEvent()
    data class ToTransactionWith(val id: Long): HomeEvent()
    data object ToCreateGoal: HomeEvent()
    data object ToGoals: HomeEvent()
    data class ToGoalWith(val id: Long): HomeEvent()
    data object ToMonthlyBudget: HomeEvent()
    data object ToWallets: HomeEvent()
}