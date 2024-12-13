package com.kakapo.oakane.presentation.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.home.HomeRoute

const val HOME_ROUTE = "home"

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    this.navigate(HOME_ROUTE, navOptions)
}

fun NavGraphBuilder.homeScreen(
    openDrawer: () -> Unit,
    navigateToAddTransaction: (Long) -> Unit,
    navigateToTransactions: () -> Unit,
    navigateToAddGoal: (Long) -> Unit,
    navigateToTransaction: (Long) -> Unit,
    navigateToGoals: () -> Unit,
    navigateToGoal: (Long) -> Unit,
    navigateToMonthlyBudget: () -> Unit
) {
    composable(HOME_ROUTE) {
        HomeRoute(
            navigateToAddTransaction = { navigateToAddTransaction.invoke(0L) },
            navigateToTransactions = navigateToTransactions,
            navigateToTransaction = navigateToTransaction,
            navigateToAddGoal = { navigateToAddGoal.invoke(0) },
            navigateToGoals = navigateToGoals,
            navigateToGoal = navigateToGoal,
            openDrawer = openDrawer,
            navigateToMonthlyBudget = navigateToMonthlyBudget
        )
    }
}