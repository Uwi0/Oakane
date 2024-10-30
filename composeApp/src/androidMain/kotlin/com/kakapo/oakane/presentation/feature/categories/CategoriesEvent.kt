package com.kakapo.oakane.presentation.feature.categories

sealed interface CategoriesEvent
data class OnTabChanged(val tabIndex: Int): CategoriesEvent
data class OnSearchQueryChanged(val query: String): CategoriesEvent