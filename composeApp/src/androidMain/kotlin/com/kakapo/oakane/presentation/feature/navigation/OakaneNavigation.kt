package com.kakapo.oakane.presentation.feature.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.kakapo.oakane.presentation.feature.addGoal.navigation.addGoalScreen
import com.kakapo.oakane.presentation.feature.addGoal.navigation.navigateToAddGoal
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.addTransactionScreen
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.navigateToAddTransaction
import com.kakapo.oakane.presentation.feature.categories.navigation.categoriesScreen
import com.kakapo.oakane.presentation.feature.home.navigation.HOME_ROUTE
import com.kakapo.oakane.presentation.feature.home.navigation.homeScreen
import com.kakapo.oakane.presentation.feature.transaction.navigation.navigateToTransaction
import com.kakapo.oakane.presentation.feature.transaction.navigation.transactionScreen
import com.kakapo.oakane.presentation.feature.transactions.navigation.navigateToTransactions
import com.kakapo.oakane.presentation.feature.transactions.navigation.transactionsScreen

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
            navigateToAddGoal = navController::navigateToAddGoal
        )
        addTransactionScreen(navigateBack = navController::popBackStack)
        transactionsScreen(
            navigateBack = navController::popBackStack,
            navigateToTransaction = navController::navigateToTransaction
        )
        transactionScreen(
            navigateToEdit = navController::navigateToAddTransaction,
            navigateBack = navController::popBackStack
        )
        categoriesScreen(
            navigateBack = navController::popBackStack
        )
        addGoalScreen(
            navigateBack = navController::popBackStack
        )
    }

}