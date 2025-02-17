package com.kakapo.oakane.presentation.feature.goals.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.goals.GoalRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER

const val GOALS_ROUTE = "goals_route"

fun NavController.navigateToGoals(showDrawer: Boolean = false, navOptions: NavOptions? = null) {
    val route = "$GOALS_ROUTE/$showDrawer"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.goalsScreen(
    openDrawer: () -> Unit,
    navigateUp: () -> Unit,
    navigateToAddGoal: (Long) -> Unit,
    navigateToGoal: (Long) -> Unit
) {
    val route = "$GOALS_ROUTE/{$SHOW_DRAWER}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { navBackStackEntry ->
        val showDrawer = navBackStackEntry.arguments?.getBoolean(SHOW_DRAWER) == true
        GoalRoute(
            showDrawer = showDrawer,
            openDrawer = openDrawer,
            navigateUp = navigateUp,
            navigateToAddGoal = { navigateToAddGoal.invoke(0) },
            navigateToGoal = navigateToGoal
        )
    }
}