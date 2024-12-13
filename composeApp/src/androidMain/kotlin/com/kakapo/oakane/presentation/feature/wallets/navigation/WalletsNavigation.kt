package com.kakapo.oakane.presentation.feature.wallets.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.wallets.WalletsRoute

const val WALLETS_ROUTE = "wallets_route"

fun NavController.navigateToWallets(navOptions: NavOptions? = null){
    navigate(WALLETS_ROUTE, navOptions)
}

fun NavGraphBuilder.walletsScreen(){
    composable(WALLETS_ROUTE){
        WalletsRoute()
    }
}