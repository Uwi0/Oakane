package com.kakapo.oakane.presentation.feature.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.goal.component.DialogAddGoalSavingView
import com.kakapo.oakane.presentation.feature.goal.component.card.CardGoalView
import com.kakapo.oakane.presentation.feature.goal.component.card.CardNoteView
import com.kakapo.oakane.presentation.feature.goal.component.card.CardTimeView
import com.kakapo.oakane.presentation.viewModel.goal.GoalEffect
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoalRoute(goalId: Long, navigateUp: () -> Unit) {
    val viewModel = koinViewModel<GoalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData(goalId)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when(effect) {
                GoalEffect.NavigateBack -> navigateUp.invoke()
            }
        }
    }

    GoalScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.dialogShown) {
        DialogAddGoalSavingView(uiState = uiState, onEvent = viewModel::handleEvent)
    }
}

@Composable
private fun GoalScreen(uiState: GoalState, onEvent: (GoalEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "My Goal",
                onNavigateBack = { onEvent.invoke(GoalEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardGoalView(uiState = uiState)
                CardTimeView(uiState = uiState)
                CardNoteView(uiState = uiState)
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent.invoke(GoalEvent.Dialog(true)) }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    )
}