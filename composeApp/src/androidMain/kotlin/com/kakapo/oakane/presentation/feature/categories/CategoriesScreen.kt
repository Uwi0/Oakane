package com.kakapo.oakane.presentation.feature.categories

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.model.category.CategoryModel
import com.kakapo.model.system.Theme
import com.kakapo.model.transaction.TransactionType
import com.kakapo.oakane.presentation.designSystem.animation.slidingContentAnimation
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabRowView
import com.kakapo.oakane.presentation.designSystem.component.tab.CustomTabView
import com.kakapo.oakane.presentation.designSystem.component.textField.SearchTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationMenuTopAppBarView
import com.kakapo.oakane.presentation.feature.categories.component.CategoriesSheetView
import com.kakapo.oakane.presentation.feature.categories.component.SwipeToDeleteCategoryView
import com.kakapo.oakane.presentation.model.CategoriesSheetContent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEffect
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesEvent
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesState
import com.kakapo.oakane.presentation.viewModel.categories.CategoriesViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CategoriesRoute(
    showDrawer: Boolean,
    openDrawer: () -> Unit,
    navigateBack: () -> Unit
) {
    val viewModel = koinViewModel<CategoriesViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            targetValue != SheetValue.Hidden || uiState.sheetContent == CategoriesSheetContent.Create
        }
    )

    LaunchedEffect(Unit) {
        viewModel.initializeData(showDrawer = showDrawer)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is CategoriesEffect.ShowError -> context.showToast(effect.message)
                CategoriesEffect.HideSheet -> sheetState.hide()
                CategoriesEffect.NavigateBack -> navigateBack.invoke()
                CategoriesEffect.OpenDrawer -> openDrawer.invoke()
            }
        }
    }

    CategoriesScreen(
        uiState = uiState,
        onEvent = viewModel::handleEvent
    )

    if (uiState.showSheet) {
        CategoriesSheetView(sheetState = sheetState, uiState = uiState, viewModel::handleEvent)
    }
}

@Composable
private fun CategoriesScreen(
    uiState: CategoriesState,
    onEvent: (CategoriesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationMenuTopAppBarView(
                title = "Categories",
                showDrawer = uiState.showDrawer,
                tonalElevation = 0.dp,
                onNavigateBack = { onEvent.invoke(CategoriesEvent.NavigateBack) },
                openMenu = { onEvent.invoke(CategoriesEvent.OpenDrawer) }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Spacer(modifier = Modifier.size(16.dp))
                SearchTextFieldView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    value = uiState.searchQuery,
                    placeholder = "Search Categories...",
                    onValueChange = { onEvent.invoke(CategoriesEvent.Search(it)) }
                )
                CategoriesTabView(uiState.selectedTab, onEvent)
                CategoriesContentView(
                    uiState.selectedTab,
                    uiState.theme,
                    uiState.filteredCategories,
                    onEvent
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent.invoke(CategoriesEvent.ShowSheet(visibility = true)) },
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
    theme: Theme,
    categories: List<CategoryModel>,
    onEvent: (CategoriesEvent) -> Unit
) {
    AnimatedContent(
        targetState = tab,
        transitionSpec = { slidingContentAnimation() },
        label = "CategoriesContentViewAnimation",
    ) { selectedTab ->
        val selectedCategories = categories.filter { it.type.ordinal == selectedTab }
        LazyColumn(
            contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(selectedCategories, key = { it.id }) { category ->
                SwipeToDeleteCategoryView(category, theme, onEvent)
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
        modifier = Modifier.padding(horizontal = 16.dp),
        tabWidth = 180.dp,
        selectedTabIndex = selectedTab,
    ) {
        TransactionType.entries.forEach {
            val selected = selectedTab == it.ordinal
            CustomTabView(
                selected = selected,
                onClick = { onEvent.invoke(CategoriesEvent.ChangeTab(it.ordinal)) }
            ) {
                Text(text = it.name)
            }
        }
    }
}