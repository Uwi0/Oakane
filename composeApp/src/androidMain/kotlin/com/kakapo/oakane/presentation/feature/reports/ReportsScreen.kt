package com.kakapo.oakane.presentation.feature.reports

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileDownload
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.reports.component.BudgetContentView
import com.kakapo.oakane.presentation.feature.reports.component.ButtonFilterView
import com.kakapo.oakane.presentation.feature.reports.component.DonutChartComponentView
import com.kakapo.oakane.presentation.feature.reports.component.ReportsItemView
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState
import com.kakapo.oakane.presentation.viewModel.reports.ReportsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ReportsRoute() {
    val viewModel = koinViewModel<ReportsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    ReportsScreen(uiState = uiState)
}

@Composable
private fun ReportsScreen(uiState: ReportsState) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Reports",
                actions = {
                    CustomIconButton(icon = Icons.Outlined.FileDownload) { }
                },
                onNavigateBack = {}
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    ButtonFilterView()
                }
                item {
                    DonutChartComponentView(
                        amount = 20_000_000_000.0,
                        proportions = uiState.proportions,
                        colorsInt = uiState.colors,
                        categoriesName = uiState.names
                    )
                }
                item {
                    BudgetContentView(item = uiState.monthlyOverView)
                }
                items(uiState.reports) { report ->
                    ReportsItemView(report)
                }
            }
        }
    )
}