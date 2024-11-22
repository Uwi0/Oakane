package com.kakapo.oakane.presentation.feature.addGoal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.common.utils.showToast
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.OutlinedCurrencyTextField
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.addGoal.component.DateSelectorView
import com.kakapo.oakane.presentation.feature.addGoal.component.ImageGoalPicker
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEffect
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEvent
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalState
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalViewModel
import com.kakapo.oakane.presentation.viewModel.addGoal.GoalDateContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddGoalRoute(
    navigateBack: () -> Unit
) {

    val context = LocalContext.current
    val viewModel = koinViewModel<AddGoalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEffect.collect { effect ->
            when (effect) {
                is AddGoalEffect.ShowError -> context.showToast("Error ${effect.message}")
                AddGoalEffect.SuccessSaveGoal -> navigateBack.invoke()
                AddGoalEffect.NavigateBack -> navigateBack.invoke()
            }
        }
    }

    AddGoalScreen(uiState = uiState, onEvent = viewModel::handleEvent)

    if (uiState.dialogShown) {
        CustomDatePickerDialog(
            initialValue = uiState.initialDateDialog,
            onDismiss = { viewModel.handleEvent(AddGoalEvent.HideDialog) },
            onConfirm = { date ->
                when(uiState.dialogContent){
                    GoalDateContent.Start -> viewModel.handleEvent(AddGoalEvent.SetStart(date))
                    GoalDateContent.End -> viewModel.handleEvent(AddGoalEvent.SetEnd(date))
                }
            }
        )
    }
}

@Composable
private fun AddGoalScreen(uiState: AddGoalState, onEvent: (AddGoalEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Add Goals",
                onNavigateBack = {}
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageGoalPicker(onSelectedImage = { onEvent.invoke(AddGoalEvent.SetFile(it))} )
                Spacer(modifier = Modifier.size(4.dp))
                OutlinedCurrencyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Target",
                    placeHolder = "0",
                    value = uiState.targetAmount,
                    prefix = "Rp ",
                    onValueChange = { onEvent.invoke(AddGoalEvent.SetTarget(it)) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Goal Name",
                    placeHolder = "My Goal",
                    value = uiState.goalName,
                    onValueChange = { onEvent.invoke(AddGoalEvent.SetName(it)) }
                )
                CustomOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Note",
                    placeHolder = "Some Note",
                    value = uiState.note,
                    onValueChange = { onEvent.invoke(AddGoalEvent.SetNote(it)) }
                )
                DateSelectorView(
                    defaultDate = uiState.startDate,
                    icon = Icons.Outlined.CalendarToday,
                    title = "Starting Date",
                    onClick = { onEvent.invoke(AddGoalEvent.ShowDialog(GoalDateContent.Start)) }
                )
                DateSelectorView(
                    defaultDate = uiState.endDate,
                    icon = Icons.Outlined.Event,
                    title = "End Date",
                    onClick = { onEvent.invoke(AddGoalEvent.ShowDialog(GoalDateContent.End)) }
                )
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                onClick = { onEvent.invoke(AddGoalEvent.SaveGoal) },
                contentPadding = PaddingValues(16.dp),
                content = {
                    Text(text = "Save Goal")
                }
            )
        }
    )
}