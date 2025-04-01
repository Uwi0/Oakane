package com.kakapo.oakane.presentation.feature.wallets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER
import com.kakapo.oakane.presentation.feature.wallets.WalletsRoute

const val WALLETS_ROUTE = "wallets_route"

fun NavController.navigateToWallets(showDrawer: Boolean = false, navOptions: NavOptions? = null) {
    val route = "$WALLETS_ROUTE/$showDrawer"
    navigate(route, navOptions)
}

fun NavGraphBuilder.walletsScreen(
    openDrawer: () -> Unit,
    navigateBack: () -> Unit,
    navigateToWallet: (Long) -> Unit,
    navigateToCreateWallet: (Long) -> Unit
) {
    val route = "$WALLETS_ROUTE/{$SHOW_DRAWER}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { backStackEntry ->
        val showDrawer = backStackEntry.arguments?.getBoolean(SHOW_DRAWER) == true
        WalletsRoute(
            showDrawer = showDrawer,
            openDrawer = openDrawer,
            navigateBack = navigateBack,
            navigateToWallet = navigateToWallet,
            navigateToCreateWallet = navigateToCreateWallet
        )
    }
}