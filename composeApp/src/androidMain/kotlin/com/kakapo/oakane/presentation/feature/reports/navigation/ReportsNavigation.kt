package com.kakapo.oakane.presentation.feature.reports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER
import com.kakapo.oakane.presentation.feature.reports.ReportsRoute

const val REPORTS_ROUTE = "reports_route"

fun NavController.navigateToReports(showDrawer: Boolean = false, navOptions: NavOptions? = null) {
    val route = "$REPORTS_ROUTE/$showDrawer"
    navigate(route, navOptions)
}

fun NavGraphBuilder.reportsScreen(openDrawer: () -> Unit, navigateBack: () -> Unit) {
    val route = "$REPORTS_ROUTE/{$SHOW_DRAWER}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { backStackEntry ->
        val showDrawer = backStackEntry.arguments?.getBoolean(SHOW_DRAWER) == true
        ReportsRoute(showDrawer = showDrawer, openDrawer = openDrawer, navigateBack = navigateBack)
    }
}