package com.kakapo.oakane.presentation.feature.goals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.common.showToast
import com.kakapo.oakane.presentation.feature.goals.component.GoalsTopAppbarView
import com.kakapo.oakane.presentation.ui.component.item.GoalItemView
import com.kakapo.oakane.presentation.viewModel.goals.GoalsEffect
import com.kakapo.oakane.presentation.viewModel.goals.GoalsEvent
import com.kakapo.oakane.presentation.viewModel.goals.GoalsState
import com.kakapo.oakane.presentation.viewModel.goals.GoalsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun GoalRoute(
    navigateUp: () -> Unit,
    navigateToAddGoal: () -> Unit,
    navigateToGoal: (Long) -> Unit
) {
    val context = LocalContext.current
    val viewModel = koinViewModel<GoalsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initData()
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                GoalsEffect.NavigateBack -> navigateUp.invoke()
                is GoalsEffect.NavigateToGoal -> navigateToGoal.invoke(effect.id)
                GoalsEffect.AddGoal -> navigateToAddGoal.invoke()
                is GoalsEffect.ShowError -> context.showToast(effect.message)
            }
        }
    }

    GoalScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@Composable
private fun GoalScreen(uiState: GoalsState, onEvent: (GoalsEvent) -> Unit) {
    Scaffold(
        topBar = {
            GoalsTopAppbarView(uiState = uiState, onEvent = onEvent)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.filteredGoals, key = { it.id }) { goal ->
                    GoalItemView(
                        goal = goal,
                        theme = uiState.theme,
                        onClicked = { onEvent.invoke(GoalsEvent.NavigateToGoal(goal.id)) }
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent.invoke(GoalsEvent.AddGoal) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}