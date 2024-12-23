package com.kakapo.oakane

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kakapo.oakane.presentation.feature.categories.navigation.navigateToCategories
import com.kakapo.oakane.presentation.feature.goals.navigation.navigateToGoals
import com.kakapo.oakane.presentation.feature.home.navigation.navigateToHome
import com.kakapo.oakane.presentation.feature.report.navigation.navigateToReport
import com.kakapo.oakane.presentation.feature.transactions.navigation.navigateToTransactions
import com.kakapo.oakane.presentation.feature.wallets.navigation.navigateToWallets
import com.kakapo.oakane.presentation.navigation.DrawerMenuNavigation

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
) = remember(navController) {
    OakaneAppState(navController)
}

@Stable
class OakaneAppState(val navController: NavHostController) {

    private val selectedDrawerValue = mutableStateOf(DrawerMenuNavigation.DASHBOARD)

    val isDashboardRoute: Boolean get() {
        return selectedDrawerValue.value == DrawerMenuNavigation.DASHBOARD
    }
    fun isSelected(menu: DrawerMenuNavigation): Boolean {
        return menu == selectedDrawerValue.value
    }

    fun navigateToCurrentMenu(menu: DrawerMenuNavigation) {
        selectedDrawerValue.value = menu
        val topLevelNavOptions = navOptionsPopBackStack()
        when (menu) {
            DrawerMenuNavigation.DASHBOARD -> navController.navigateToHome(topLevelNavOptions)
            DrawerMenuNavigation.TRANSACTIONS -> navController.navigateToTransactions()
            DrawerMenuNavigation.CATEGORIES -> navController.navigateToCategories()
            DrawerMenuNavigation.Goals -> navController.navigateToGoals()
            DrawerMenuNavigation.WALLETS -> navController.navigateToWallets()
            DrawerMenuNavigation.Report -> navController.navigateToReport()
        }
    }

    private fun navOptionsPopBackStack() = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }

}