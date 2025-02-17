package com.kakapo.oakane.presentation.feature.transactions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER
import com.kakapo.oakane.presentation.feature.transactions.TransactionsRoute

const val TRANSACTIONS_ROUTE = "transactions_route"

fun NavController.navigateToTransactions(showDrawer: Boolean = false,navOptions: NavOptions? = null) {
    val route = "$TRANSACTIONS_ROUTE/$showDrawer"
    navigate(route, navOptions)
}

fun NavGraphBuilder.transactionsScreen(
    openDrawer: () -> Unit,
    navigateBack: () -> Unit,
    navigateToTransaction: (Long) -> Unit
) {
    val route = "$TRANSACTIONS_ROUTE/{${SHOW_DRAWER}}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { backStack ->
        val showDrawer = backStack.arguments?.getBoolean(SHOW_DRAWER) == true
        TransactionsRoute(
            openDrawer = openDrawer,
            showDrawer = showDrawer,
            navigateBack = navigateBack,
            navigateToTransaction = navigateToTransaction
        )
    }
}