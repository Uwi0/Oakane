package com.kakapo.oakane.presentation.feature.goals.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.goals.GoalRoute

const val GOALS_ROUTE = "goals_route"

fun NavController.navigateToGoals(navOptions: NavOptions? = null) {
    this.navigate(GOALS_ROUTE, navOptions)
}

fun NavGraphBuilder.goalsScreen(navigateUp: () -> Unit) {
    composable(GOALS_ROUTE) {
        GoalRoute(navigateUp = navigateUp)
    }
}