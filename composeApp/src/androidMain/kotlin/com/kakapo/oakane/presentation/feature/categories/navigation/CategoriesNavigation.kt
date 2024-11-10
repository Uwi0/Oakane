package com.kakapo.oakane.presentation.feature.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.kakapo.oakane.presentation.feature.categories.CategoriesRoute

const val CATEGORIES_ROUTE = "categories"

fun NavController.navigateToCategories(navOptions: NavOptions? = null) {
    navigate(CATEGORIES_ROUTE, navOptions)
}

fun NavGraphBuilder.categoriesScreen(
    navigateBack: () -> Unit
) {
    composable(CATEGORIES_ROUTE) {
        CategoriesRoute(navigateBack = navigateBack)
    }
}