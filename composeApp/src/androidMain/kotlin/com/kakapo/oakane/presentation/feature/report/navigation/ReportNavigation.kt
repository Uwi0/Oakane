package com.kakapo.oakane.presentation.feature.report.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.report.ReportRoute

const val REPORT_ROUTE = "report_route"

fun NavController.navigateToReport(navOptions: NavOptions? = null) {
    navigate(REPORT_ROUTE, navOptions)
}

fun NavGraphBuilder.reportScreen(){
    composable(REPORT_ROUTE){
        ReportRoute()
    }
}