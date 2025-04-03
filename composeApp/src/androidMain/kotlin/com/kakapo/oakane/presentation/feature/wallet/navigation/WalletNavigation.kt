package com.kakapo.oakane.presentation.feature.wallet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.navigation.NavArgs
import com.kakapo.oakane.presentation.feature.wallet.WalletRoute

const val WALLET_ROUTE = "wallet_route"

fun NavController.navigateToWallet(walletId: Long, navOptions: NavOptions? = null) {
    val route = "$WALLET_ROUTE/$walletId"
    this.navigate(route, navOptions)
}

fun NavGraphBuilder.walletScreen(navigateBack: () -> Unit, navigateToCreateWallet: (Long) -> Unit) {
    val route = "$WALLET_ROUTE/{${NavArgs.WALLET_ID}}"
    val arguments = listOf(navArgument(NavArgs.WALLET_ID) { type = NavType.LongType })
    composable(route, arguments) { backStackEntry ->
        val walletId = backStackEntry.arguments?.getLong(NavArgs.WALLET_ID) ?: 0L
        WalletRoute(
            walletId = walletId,
            navigateBack = navigateBack,
            navigateToCreateWallet = navigateToCreateWallet
        )
    }
}