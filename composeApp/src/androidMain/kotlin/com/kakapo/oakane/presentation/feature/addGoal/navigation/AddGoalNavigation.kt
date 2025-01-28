package com.kakapo.oakane.presentation.feature.addGoal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.addGoal.AddGoalRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs

const val ADD_GOAL_ROUTE = "add_goal_route"

fun NavController.navigateToAddGoal(goalId: Long, navOptions: NavOptions? = null) {
    val route = "$ADD_GOAL_ROUTE/$goalId"
    navigate(route, navOptions)
}

fun NavGraphBuilder.addGoalScreen(
    navigateBack: () -> Unit
) {
    val route = "$ADD_GOAL_ROUTE/{${NavArgs.GOAL_ID_ARGS}}"
    val arguments = listOf(navArgument(NavArgs.GOAL_ID_ARGS) { type = NavType.LongType })
    composable(route = route, arguments = arguments) { backStackEntry ->
        val backStackArguments = requireNotNull(backStackEntry.arguments)
        val goalId = backStackArguments.getLong(NavArgs.GOAL_ID_ARGS)
        AddGoalRoute(goalId = goalId,navigateBack = navigateBack)
    }
}