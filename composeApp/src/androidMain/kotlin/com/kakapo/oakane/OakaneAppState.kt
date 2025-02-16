package com.kakapo.oakane

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.kakapo.oakane.presentation.feature.categories.navigation.navigateToCategories
import com.kakapo.oakane.presentation.feature.goals.navigation.navigateToGoals
import com.kakapo.oakane.presentation.feature.home.navigation.HOME_ROUTE
import com.kakapo.oakane.presentation.feature.home.navigation.navigateToHome
import com.kakapo.oakane.presentation.feature.reports.navigation.navigateToReports
import com.kakapo.oakane.presentation.feature.settings.navigation.navigateToSettings
import com.kakapo.oakane.presentation.feature.transactions.navigation.navigateToTransactions
import com.kakapo.oakane.presentation.feature.wallets.navigation.navigateToWallets
import com.kakapo.oakane.presentation.navigation.DrawerMenuNavigation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(navController) {

    OakaneAppState(navController, coroutineScope)
}

@Stable
class OakaneAppState(val navController: NavHostController, private val coroutineScope: CoroutineScope) {

    private var selectedDrawerValue by mutableStateOf(DrawerMenuNavigation.DASHBOARD)
    private var isNavigateUp = false

    val currentRoute get() = _currentRoute.value
    private val _currentRoute = mutableStateOf<String?>(null)

    init {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            _currentRoute.value = destination.route
        }
    }

    fun isSelected(menu: DrawerMenuNavigation): Boolean {
        return menu == selectedDrawerValue
    }

    fun navigateToCurrentMenu(menu: DrawerMenuNavigation) {
        selectedDrawerValue = menu
        val topLevelNavOptions = navOptionsPopBackStack()
        when (menu) {
            DrawerMenuNavigation.DASHBOARD -> navController.navigateToHome(topLevelNavOptions)
            DrawerMenuNavigation.TRANSACTIONS -> navController.navigateToTransactions()
            DrawerMenuNavigation.CATEGORIES -> navController.navigateToCategories()
            DrawerMenuNavigation.Goals -> navController.navigateToGoals()
            DrawerMenuNavigation.WALLETS -> navController.navigateToWallets()
            DrawerMenuNavigation.Reports -> navController.navigateToReports()
            DrawerMenuNavigation.SETTINGS -> navController.navigateToSettings()
        }
    }

    fun navOptionsPopBackStack() = navOptions {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
            inclusive = true
        }
        launchSingleTop = true
        restoreState = true
    }

    fun isDashboardRoute(): Boolean = currentRoute?.let { current ->
        current == HOME_ROUTE
    } == true

    fun safeNavigateUp() {
        if (!isNavigateUp) {
            isNavigateUp = true
            navController.navigateUp()
            coroutineScope.launch {
                delay(750)
                isNavigateUp = false
            }
        }
    }

}

