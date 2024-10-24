package com.kakapo.oakane.presentation.feature.transaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.navigation.NavigationArgs
import com.kakapo.oakane.presentation.feature.transaction.TransactionRoute

const val TRANSACTION_ROUTE = "transaction_route"

fun NavController.navigateToTransaction(id: Long, navOptions: NavOptions? = null) {
    val route = "$TRANSACTION_ROUTE/$id"
    navigate(route, navOptions = navOptions)
}

fun NavGraphBuilder.transactionScreen(navigateToEdit: (Long) -> Unit) {
    val route = "$TRANSACTION_ROUTE/{${NavigationArgs.TRANSACTION_ID}}"
    val arguments = listOf(navArgument(NavigationArgs.TRANSACTION_ID) { type = NavType.LongType })
    composable(route = route, arguments = arguments) { navBackEntry ->
        val transactionId = navBackEntry.arguments?.getLong(NavigationArgs.TRANSACTION_ID) ?: 0L
        TransactionRoute(transactionId = transactionId, navigateToEdit = navigateToEdit)
    }
}