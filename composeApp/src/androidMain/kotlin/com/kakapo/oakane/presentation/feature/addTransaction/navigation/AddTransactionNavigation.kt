package com.kakapo.oakane.presentation.feature.addTransaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.addTransaction.AddTransactionRoute

const val ADD_TRANSACTION_ROUTE = "add_transaction"

fun NavController.navigateToAddTransaction(navOptions: NavOptions? = null) {
    this.navigate(ADD_TRANSACTION_ROUTE, navOptions)
}

fun NavGraphBuilder.addTransactionScreen(navigateBack: () -> Unit) {
    composable(route = ADD_TRANSACTION_ROUTE) {
        AddTransactionRoute(navigateBack = navigateBack)
    }
}