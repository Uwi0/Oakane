package com.kakapo.oakane.presentation.feature.addTransaction.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.designSystem.animation.slideComposable
import com.kakapo.oakane.presentation.feature.addTransaction.AddTransactionRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs

const val ADD_TRANSACTION_ROUTE = "add_transaction"

fun NavController.navigateToAddTransaction(
    transactionId: Long,
    navOptions: NavOptions? = null
) {
    val route = "$ADD_TRANSACTION_ROUTE/$transactionId"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.addTransactionScreen(navigateBack: () -> Unit) {
    val route = "$ADD_TRANSACTION_ROUTE/{${NavArgs.TRANSACTION_ID}}"
    val arguments = listOf(navArgument(NavArgs.TRANSACTION_ID) { type = NavType.LongType })
    slideComposable(
        route = route,
        arguments = arguments,
    ) { backStackEntry ->
        val transactionId = backStackEntry.arguments?.getLong(NavArgs.TRANSACTION_ID) ?: 0L
        AddTransactionRoute(transactionId, navigateBack = navigateBack)
    }
}