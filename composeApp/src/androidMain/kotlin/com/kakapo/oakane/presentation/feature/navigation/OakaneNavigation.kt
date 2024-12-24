package com.kakapo.oakane.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kakapo.oakane.presentation.feature.addGoal.navigation.addGoalScreen
import com.kakapo.oakane.presentation.feature.addGoal.navigation.navigateToAddGoal
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.addTransactionScreen
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.navigateToAddTransaction
import com.kakapo.oakane.presentation.feature.categories.navigation.categoriesScreen
import com.kakapo.oakane.presentation.feature.goal.navigation.goalScreen
import com.kakapo.oakane.presentation.feature.goal.navigation.navigateToGoal
import com.kakapo.oakane.presentation.feature.goals.navigation.goalsScreen
import com.kakapo.oakane.presentation.feature.goals.navigation.navigateToGoals
import com.kakapo.oakane.presentation.feature.home.navigation.HOME_ROUTE
import com.kakapo.oakane.presentation.feature.home.navigation.homeScreen
import com.kakapo.oakane.presentation.feature.monthlyBudget.navigation.monthlyBudgetScreen
import com.kakapo.oakane.presentation.feature.monthlyBudget.navigation.navigateToMonthlyBudget
import com.kakapo.oakane.presentation.feature.reports.navigation.reportsScreen
import com.kakapo.oakane.presentation.feature.transaction.navigation.navigateToTransaction
import com.kakapo.oakane.presentation.feature.transaction.navigation.transactionScreen
import com.kakapo.oakane.presentation.feature.transactions.navigation.navigateToTransactions
import com.kakapo.oakane.presentation.feature.transactions.navigation.transactionsScreen
import com.kakapo.oakane.presentation.feature.wallets.navigation.navigateToWallets
import com.kakapo.oakane.presentation.feature.wallets.navigation.walletsScreen

@Composable
internal fun OakaneNavHost(
    navController: NavHostController,
    startDestination: String = HOME_ROUTE,
    openDrawer: () -> Unit
) {
    NavHost(navController = navController, startDestination = startDestination) {
        homeScreen(
            openDrawer = openDrawer,
            navigateToAddTransaction = navController::navigateToAddTransaction,
            navigateToTransactions = navController::navigateToTransactions,
            navigateToTransaction = navController::navigateToTransaction,
            navigateToAddGoal = navController::navigateToAddGoal,
            navigateToGoals = navController::navigateToGoals,
            navigateToGoal = navController::navigateToGoal,
            navigateToMonthlyBudget = navController::navigateToMonthlyBudget,
            navigateToWallets = navController::navigateToWallets
        )
        addTransactionScreen(navigateBack = navController::navigateUp)
        transactionsScreen(
            navigateBack = navController::navigateUp,
            navigateToTransaction = navController::navigateToTransaction
        )
        transactionScreen(
            navigateToEdit = navController::navigateToAddTransaction,
            navigateBack = navController::navigateUp
        )
        categoriesScreen(
            navigateBack = navController::navigateUp
        )
        addGoalScreen(
            navigateBack = navController::navigateUp
        )
        goalsScreen(
            navigateUp = navController::navigateUp,
            navigateToAddGoal = navController::navigateToAddGoal,
            navigateToGoal = navController::navigateToGoal
        )
        goalScreen(
            navigateUp = navController::navigateUp,
            updateGoal = navController::navigateToAddGoal
        )
        monthlyBudgetScreen(
            navigateBack = navController::navigateUp
        )
        walletsScreen(
            navigateBack = navController::navigateUp
        )
        reportsScreen()
    }
}