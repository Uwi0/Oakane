package com.kakapo.oakane.presentation.feature.addTransaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.addTransaction.AddTransactionRoute
import com.kakapo.oakane.presentation.feature.navigation.NavigationArgs

const val ADD_TRANSACTION_ROUTE = "add_transaction"

fun NavController.navigateToAddTransaction(
    transactionId: Long,
    navOptions: NavOptions? = null
) {
    val route = "$ADD_TRANSACTION_ROUTE/$transactionId"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.addTransactionScreen(navigateBack: () -> Unit) {
    val route = "$ADD_TRANSACTION_ROUTE/{${NavigationArgs.TRANSACTION_ID}}"
    val arguments = listOf(navArgument(NavigationArgs.TRANSACTION_ID) { type = NavType.LongType })
    composable(route = route, arguments = arguments) { backStackEntry ->
        val transactionId = backStackEntry.arguments?.getLong(NavigationArgs.TRANSACTION_ID) ?: 0L
        AddTransactionRoute(transactionId, navigateBack = navigateBack)
    }
}