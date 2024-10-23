package com.kakapo.oakane.presentation.feature.transactions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.transactions.TransactionsRoute

const val TRANSACTIONS_ROUTE = "transactions_route"

fun NavController.navigateToTransactions(navOptions: NavOptions? = null) {
    navigate(TRANSACTIONS_ROUTE, navOptions)
}

fun NavGraphBuilder.transactionsScreen(
    navigateBack: () -> Unit,
    navigateToTransaction: (Long) -> Unit
) {
    composable(TRANSACTIONS_ROUTE) {
        TransactionsRoute(
            navigateBack = navigateBack,
            navigateToTransaction = navigateToTransaction
        )
    }
}