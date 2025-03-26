package com.kakapo.oakane.presentation.feature.reminder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val REMINDER_NAVIGATION = "reminder_navigation"

fun NavController.navigateToReminder(navOptions: NavOptions? = null) {
    navigate(REMINDER_NAVIGATION, navOptions)
}

fun NavGraphBuilder.reminderScreen() {
    composable(REMINDER_NAVIGATION) {

    }
}