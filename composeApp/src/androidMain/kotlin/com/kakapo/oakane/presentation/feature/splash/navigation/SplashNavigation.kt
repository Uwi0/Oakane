package com.kakapo.oakane.presentation.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.splash.SplashRoute

const val SPLASH_ROUTE = "splash_route"

fun NavGraphBuilder.splashScreen(
    navigateToTermAndService: () -> Unit,
    navigateToHome: () -> Unit,
    navigateToOnBoarding: () -> Unit
) {
    composable(SPLASH_ROUTE) {
        SplashRoute(
            navigateToTermAndService = navigateToTermAndService,
            navigateToHome = navigateToHome,
            navigateToOnBoarding = navigateToOnBoarding
        )
    }
}