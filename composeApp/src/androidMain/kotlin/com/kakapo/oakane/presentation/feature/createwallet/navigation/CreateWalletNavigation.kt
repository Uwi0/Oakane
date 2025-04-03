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

fun NavController.navigateToCreateWallet(
    id: Long,
    isFromOnBoarding: Boolean,
    navOptions: NavOptions? = null
) {
    val route = "$CREATE_WALLET_NAVIGATION/$id/$isFromOnBoarding"
    navigate(route, navOptions)
}

fun NavGraphBuilder.createWalletScreen(navigateBack: () -> Unit, navigateToHome: () -> Unit) {
    val route = "$CREATE_WALLET_NAVIGATION/{${NavArgs.WALLET_ID}}/{${NavArgs.IS_FROM_ON_BOARDING}}"
    val args = listOf(
        navArgument(NavArgs.WALLET_ID) { type = NavType.LongType },
        navArgument(NavArgs.IS_FROM_ON_BOARDING) { type = NavType.BoolType }
    )
    composable(route, args) { backStackEntry ->
        val arguments = backStackEntry.arguments
        val walletId = arguments?.getLong(NavArgs.WALLET_ID) ?: 0
        val isFromOnBoarding = arguments?.getBoolean(NavArgs.IS_FROM_ON_BOARDING) == true

        CreateWalletRoute(
            walletId = walletId,
            isFromOnboarding = isFromOnBoarding,
            navigateBack = navigateBack,
            navigateToHome = navigateToHome
        )
    }
}