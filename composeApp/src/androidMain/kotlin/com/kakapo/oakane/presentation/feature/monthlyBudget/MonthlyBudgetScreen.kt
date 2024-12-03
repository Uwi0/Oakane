package com.kakapo.oakane.presentation.feature.monthlyBudget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyBottomContentView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyTopContentView
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MonthlyBudgetRoute() {
    val viewModel = koinViewModel<MonthlyBudgetViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MonthlyBudgetScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun MonthlyBudgetScreen(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(title = "Monthly Budget") { }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MonthlyTopContentView(uiState = uiState, onEvent = onEvent)
                MonthlyBottomContentView()
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                onClick = {}) {
                Text("Save Budget")
            }
        }
    )
}

