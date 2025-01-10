package com.kakapo.oakane.presentation.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.onboarding.OnBoardingRoute

const val ON_BOARDING_ROUTE = "on_boarding_route"

fun NavGraphBuilder.onBoardingScreen(navigateToHome: () -> Unit) {
    composable(ON_BOARDING_ROUTE) {
        OnBoardingRoute(navigateToHome = navigateToHome)
    }
}