package com.kakapo.oakane.presentation.feature.createwallet.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.createwallet.CreateWalletRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs

const val CREATE_WALLET_NAVIGATION = "create_wallet_navigation"

fun NavController.navigateToCreateWallet(id: Long,navOptions: NavOptions? = null) {
    val route = "$CREATE_WALLET_NAVIGATION/$id"
    navigate(route, navOptions)
}

fun NavGraphBuilder.createWalletScreen(navigateBack: () -> Unit) {
    val route = "$CREATE_WALLET_NAVIGATION/{${NavArgs.WALLET_ID}}"
    val args = listOf(navArgument(NavArgs.WALLET_ID) { type = NavType.LongType })
    composable(route, args) { backStackEntry ->
        val walletId = backStackEntry.arguments?.getLong(NavArgs.WALLET_ID) ?: 0
        CreateWalletRoute(walletId = walletId,onNavigateBack = navigateBack)
    }
}