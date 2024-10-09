package com.kakapo.oakane

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.ADD_TRANSACTION_ROUTE
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.addTransactionScreen
import com.kakapo.oakane.presentation.feature.addTransaction.navigation.navigateToAddTransaction
import com.kakapo.oakane.presentation.feature.home.navigation.HOME_ROUTE
import com.kakapo.oakane.presentation.feature.home.navigation.homeScreen

@Composable
internal fun OakaneNavHost(
    startDestination: String = HOME_ROUTE
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        homeScreen(navigateToAddTransaction = navController::navigateToAddTransaction)
        addTransactionScreen(navigateBack = navController::popBackStack)
    }

}