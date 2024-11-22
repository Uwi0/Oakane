package com.kakapo.oakane.presentation.feature.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.goal.GoalRoute

const val GOAL_ROUTE = "goal"

fun NavController.navigateToGoal(navOptions: NavOptions? = null) {
    navigate(GOAL_ROUTE, navOptions)
}

fun NavGraphBuilder.goalScreen() {
    composable(GOAL_ROUTE) {
        GoalRoute()
    }
}