package com.kakapo.oakane.presentation.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.model.system.Theme
import com.kakapo.oakane.presentation.feature.settings.SettingsRoute

const val SETTINGS_ROUTE = "settings"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(SETTINGS_ROUTE, navOptions)
}

fun NavGraphBuilder.settingsScreen(navigateBack: () -> Unit, onSelectedTheme: (Theme) -> Unit) {
    composable(SETTINGS_ROUTE) {
        SettingsRoute(
            navigateBack = navigateBack,
            onSelectedTheme = onSelectedTheme
        )
    }
}