package com.kakapo.oakane.presentation.feature.monthlyBudget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyBottomContentView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyTopContentView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog.AddCategoryLimitDialogView
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEffect
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MonthlyBudgetRoute(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<MonthlyBudgetViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                MonthlyBudgetEffect.NavigateBack -> navigateBack.invoke()
                is MonthlyBudgetEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    MonthlyBudgetScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.dialogShown) {
        AddCategoryLimitDialogView(uiState = uiState, onEvent = viewModel::handleEvent)
    }
}

@Composable
private fun MonthlyBudgetScreen(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Monthly Budget",
                onNavigateBack = { onEvent.invoke(MonthlyBudgetEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MonthlyTopContentView(uiState = uiState, onEvent = onEvent)
                if (uiState.isEditMode) {
                    MonthlyBottomContentView(uiState = uiState, onEvent = onEvent)
                } else {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = "you may set some expense limits for each category, after adding your budget",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                onClick = { onEvent.invoke(MonthlyBudgetEvent.Save) },
                contentPadding = PaddingValues(16.dp)
            ) {
                Text("Save Budget")
            }
        }
    )
}

