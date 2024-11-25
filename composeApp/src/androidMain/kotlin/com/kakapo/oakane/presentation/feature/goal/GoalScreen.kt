package com.kakapo.oakane.presentation.feature.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.goal.component.CardGoalView
import com.kakapo.oakane.presentation.feature.goal.component.CardNoteView
import com.kakapo.oakane.presentation.feature.goal.component.CardTimeView
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoalRoute(goalId: Long) {
    val viewModel = koinViewModel<GoalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData(goalId)
    }

    GoalScreen(uiState = uiState)
}

@Composable
private fun GoalScreen(uiState: GoalState) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "My Goal",
                onNavigateBack = {}
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
        }
    )
}