package com.kakapo.oakane.presentation.feature.termAndService.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.termAndService.TermAndServiceRoute

const val TERM_AND_SERVICE_ROUTE = "termAndService"

fun NavController.navigateToTermAndService() {
    navigate(TERM_AND_SERVICE_ROUTE)
}

fun NavGraphBuilder.termAndServiceScreen() {
    composable(TERM_AND_SERVICE_ROUTE) {
        TermAndServiceRoute()
    }
}