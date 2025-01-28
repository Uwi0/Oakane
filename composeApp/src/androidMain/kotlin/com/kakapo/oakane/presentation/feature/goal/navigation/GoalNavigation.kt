package com.kakapo.oakane.presentation.feature.goal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.goal.GoalRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.GOAL_ID_ARGS

const val GOAL_ROUTE = "goal"

fun NavController.navigateToGoal(goalId: Long,navOptions: NavOptions? = null) {
    val route = "$GOAL_ROUTE/$goalId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.goalScreen(navigateUp: () -> Unit, updateGoal: (Long) -> Unit) {
    val route = "$GOAL_ROUTE/{$GOAL_ID_ARGS}"
    val arguments = listOf(navArgument(GOAL_ID_ARGS) { type = NavType.LongType })
    composable(route = route, arguments = arguments) { backStackEntry ->
        val backStackArguments = requireNotNull(backStackEntry.arguments)
        val goalId = backStackArguments.getLong(GOAL_ID_ARGS)
        GoalRoute(goalId = goalId, navigateUp = navigateUp, updateGoal = updateGoal)
    }
}