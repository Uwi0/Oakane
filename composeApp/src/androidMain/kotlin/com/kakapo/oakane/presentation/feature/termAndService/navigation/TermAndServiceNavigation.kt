package com.kakapo.oakane.presentation.feature.termAndService.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.termAndService.TermAndServiceRoute

const val TERM_AND_SERVICE_ROUTE = "termAndService"

fun NavController.navigateToTermAndService(navOptions: NavOptions? = null) {
    navigate(TERM_AND_SERVICE_ROUTE, navOptions)
}

fun NavGraphBuilder.termAndServiceScreen(navigateToOnBoarding: () -> Unit) {
    composable(TERM_AND_SERVICE_ROUTE) {
        TermAndServiceRoute(navigateToOnBoarding)
    }
}