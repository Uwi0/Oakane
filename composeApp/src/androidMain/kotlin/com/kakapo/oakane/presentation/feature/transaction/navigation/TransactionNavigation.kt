package com.kakapo.oakane.presentation.feature.transaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.transaction.TransactionRoute

const val TRANSACTION_ROUTE = "transaction_route"
const val TRANSACTION_ID_KEY = "transactionId"

fun NavController.navigateToTransaction(id: Long, navOptions: NavOptions? = null) {
    val route = "$TRANSACTION_ROUTE/$id"
    navigate(route, navOptions = navOptions)
}

fun NavGraphBuilder.transactionScreen() {
    val route = "$TRANSACTION_ROUTE/{$TRANSACTION_ID_KEY}"
    val arguments = listOf(navArgument(TRANSACTION_ID_KEY) { type = NavType.LongType })
    composable(route = route, arguments = arguments) { navBackEntry ->
        val transactionId = navBackEntry.arguments?.getLong(TRANSACTION_ID_KEY) ?: 0L
        TransactionRoute(transactionId = transactionId)
    }
}