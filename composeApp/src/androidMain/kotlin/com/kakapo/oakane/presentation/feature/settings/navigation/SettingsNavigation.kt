package com.kakapo.oakane.presentation.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.model.system.Theme
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER
import com.kakapo.oakane.presentation.feature.settings.SettingsRoute

const val SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings(showDrawer: Boolean = false,navOptions: NavOptions? = null) {
    val route = "$SETTINGS_ROUTE/$showDrawer"
    navigate(route, navOptions)
}

fun NavGraphBuilder.settingsScreen(
    openDrawer: () -> Unit,
    navigateBack: () -> Unit,
    onSelectedTheme: (Theme) -> Unit,
    navigateToReminder: () -> Unit
) {
    val route = "$SETTINGS_ROUTE/{$SHOW_DRAWER}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { backStackEntry ->
        val showDrawer = backStackEntry.arguments?.getBoolean(SHOW_DRAWER) == true
        SettingsRoute(
            showDrawer = showDrawer,
            openDrawer = openDrawer,
            navigateBack = navigateBack,
            onSelectedTheme = onSelectedTheme,
            navigateToReminder = navigateToReminder
        )
    }
}