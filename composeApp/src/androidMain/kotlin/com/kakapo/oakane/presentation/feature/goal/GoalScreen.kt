package com.kakapo.oakane.presentation.feature.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.kakapo.oakane.presentation.designSystem.component.button.CustomIconButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.goal.component.DialogGoalView
import com.kakapo.oakane.presentation.feature.goal.component.GoalSavingItemView
import com.kakapo.oakane.presentation.feature.goal.component.card.CardGoalView
import com.kakapo.oakane.presentation.feature.goal.component.card.CardTimeView
import com.kakapo.oakane.presentation.ui.component.item.CardNoteView
import com.kakapo.oakane.presentation.viewModel.goal.GoalDialogContent
import com.kakapo.oakane.presentation.viewModel.goal.GoalEffect
import com.kakapo.oakane.presentation.viewModel.goal.GoalEvent
import com.kakapo.oakane.presentation.viewModel.goal.GoalState
import com.kakapo.oakane.presentation.viewModel.goal.GoalViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoalRoute(goalId: Long, navigateUp: () -> Unit, updateGoal: (Long) -> Unit) {
    val context = LocalContext.current
    val viewModel = koinViewModel<GoalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initializeData(goalId)
    }

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                GoalEffect.NavigateBack -> navigateUp.invoke()
                is GoalEffect.ShowError -> context.showToast(effect.message)
                is GoalEffect.UpdateGoalBy -> updateGoal.invoke(effect.id)
            }
        }
    }

    GoalScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.dialogShown) {
        DialogGoalView(uiState = uiState, onEvent = viewModel::handleEvent)
    }
}

@Composable
private fun GoalScreen(uiState: GoalState, onEvent: (GoalEvent) -> Unit) {
    Scaffold(
        topBar = {
            GoalTopAppbar(onEvent)
        },
        content = { paddingValues ->
            GoalContent(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp),
                uiState = uiState
            )
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

@Composable
private fun GoalContent(modifier: Modifier = Modifier, uiState: GoalState) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GoalTopContent(uiState)
        GoalBottomContent(uiState)
    }
}

@Composable
private fun GoalTopContent(uiState: GoalState) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CardGoalView(uiState = uiState)
        CardTimeView(uiState = uiState)
        CardNoteView(note = uiState.goal.note, uiState.theme)
        Text(text = "Log Saving", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun GoalBottomContent(uiState: GoalState) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            bottom = 16.dp,
            top = 4.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(uiState.goalSavings, key = { it.id }) { saving ->
            GoalSavingItemView(
                item = saving,
                theme = uiState.theme,
                currency = uiState.currency
            )
        }
    }
}

@Composable
private fun GoalTopAppbar(onEvent: (GoalEvent) -> Unit) {
    val dialogEvent = GoalEvent.Dialog(shown = true, content = GoalDialogContent.DeleteGoal)
    CustomNavigationTopAppBarView(
        title = "My Goal",
        onNavigateBack = { onEvent.invoke(GoalEvent.NavigateBack) },
        actions = {
            CustomIconButton(
                icon = Icons.Outlined.Edit,
                onClick = { onEvent.invoke(GoalEvent.UpdateGoal) }
            )
            CustomIconButton(
                icon = Icons.Outlined.Delete,
                onClick = { onEvent.invoke(dialogEvent) }
            )
        }
    )
}