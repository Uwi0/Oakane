package com.kakapo.oakane.presentation.feature.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.category.CategoryModel
import com.kakapo.oakane.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.animation.slidingContentAnimation
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabRowView
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.categories.component.CategoryItemView
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CategoriesRoute() {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val categories by viewModel.categories.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    val onEvent: (CategoriesEvent) -> Unit = { event ->
        when (event) {
            is OnTabChanged -> viewModel.onSelectedTab(event.tabIndex)
        }
    }

    CategoriesScreen(
        categories = categories,
        selectedTab = selectedTab,
        onEvent = onEvent
    )
}

@Composable
private fun CategoriesScreen(
    categories: List<CategoryModel>,
    selectedTab: Int,
    onEvent: (CategoriesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Categories",
                onNavigateBack = {}
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                CategoriesTabView(selectedTab, onEvent)
                CategoriesContentView(selectedTab, categories)

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
                content = {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                }
            )
        }
    )
}

@Composable
private fun CategoriesContentView(
    tab: Int,
    categories: List<CategoryModel>
) {
    AnimatedContent(
        targetState = tab,
        transitionSpec = { slidingContentAnimation() },
    ) { selectedTab ->
        val selectedCategories = categories.filter { it.type.ordinal == selectedTab }
        LazyColumn(
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(selectedCategories, key = { it.id }) { category ->
                CategoryItemView(category)
            }
        }
    }
}

@Composable
private fun CategoriesTabView(
    selectedTab: Int,
    onEvent: (CategoriesEvent) -> Unit
) {
    CustomTabRowView(
        selectedTabIndex = selectedTab,
    ) {
        TransactionType.entries.forEach {
            val selected = selectedTab == it.ordinal
            CustomTabView(
                selected = selected,
                onClick = { onEvent.invoke(OnTabChanged(it.ordinal)) }
            ) {
                Text(text = it.name)
            }
        }
    }
}