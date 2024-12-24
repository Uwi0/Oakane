package com.kakapo.oakane.presentation.feature.reports.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.reports.ReportsRoute

const val REPORTS_ROUTE = "reports_route"

fun NavController.navigateToReports(navOptions: NavOptions? = null) {
    navigate(REPORTS_ROUTE, navOptions)
}

fun NavGraphBuilder.reportsScreen(){
    composable(REPORTS_ROUTE){
        ReportsRoute()
    }
}