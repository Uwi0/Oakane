package com.kakapo.oakane.presentation.feature.addGoal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.addGoal.AddGoalRoute

const val ADD_GOAL_ROUTE = "add_goal_route"

fun NavController.navigateToAddGoal(navOptions: NavOptions? = null) {
    navigate(ADD_GOAL_ROUTE, navOptions)
}

fun NavGraphBuilder.addGoalScreen(
    navigateBack: () -> Unit
) {
    composable(ADD_GOAL_ROUTE) {
        AddGoalRoute(navigateBack = navigateBack)
    }
}