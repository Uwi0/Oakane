package com.kakapo.oakane.presentation.feature.reminder.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.reminder.ReminderRoute

const val REMINDER_NAVIGATION = "reminder_navigation"

fun NavController.navigateToReminder(navOptions: NavOptions? = null) {
    navigate(REMINDER_NAVIGATION, navOptions)
}

fun NavGraphBuilder.reminderScreen(navigateBack: () -> Unit) {
    composable(REMINDER_NAVIGATION) {
        ReminderRoute(navigateBack = navigateBack)
    }
}