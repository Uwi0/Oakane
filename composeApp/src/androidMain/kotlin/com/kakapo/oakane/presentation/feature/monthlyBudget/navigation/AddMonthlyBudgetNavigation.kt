package com.kakapo.oakane.presentation.feature.monthlyBudget.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.monthlyBudget.MonthlyBudgetRoute

const val MONTHLY_BUDGET_ROUTE = "add_monthly_budget_route"

fun NavController.navigateToMonthlyBudget(navOptions: NavOptions? = null) {
    navigate(MONTHLY_BUDGET_ROUTE, navOptions)
}

fun NavGraphBuilder.monthlyBudgetScreen(navigateBack: () -> Unit) {
    composable(MONTHLY_BUDGET_ROUTE) {
        MonthlyBudgetRoute(navigateBack = navigateBack)
    }
}