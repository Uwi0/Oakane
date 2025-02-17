package com.kakapo.oakane.presentation.feature.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kakapo.oakane.presentation.feature.categories.CategoriesRoute
import com.kakapo.oakane.presentation.feature.navigation.NavArgs.SHOW_DRAWER

const val CATEGORIES_ROUTE = "categories"

fun NavController.navigateToCategories(showDrawer: Boolean = false, navOptions: NavOptions? = null) {
    val route = "$CATEGORIES_ROUTE/$showDrawer"
    navigate(route, navOptions)
}

fun NavGraphBuilder.categoriesScreen(
    openDrawer: () -> Unit,
    navigateBack: () -> Unit
) {
    val route = "$CATEGORIES_ROUTE/{${SHOW_DRAWER}}"
    val args = listOf(navArgument(SHOW_DRAWER) { type = NavType.BoolType })
    composable(route, args) { backStack ->
        val showDrawer = backStack.arguments?.getBoolean(SHOW_DRAWER) == true
        CategoriesRoute(
            showDrawer = showDrawer,
            openDrawer = openDrawer,
            navigateBack = navigateBack
        )
    }
}