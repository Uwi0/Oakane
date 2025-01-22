package com.kakapo.oakane.presentation.feature.addGoal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.kakapo.common.showToast
import com.kakapo.common.toDateWith
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomClickableOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.CustomOutlinedTextField
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.CurrencyTextFieldConfig
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.OutlinedCurrencyTextFieldView
import com.kakapo.oakane.presentation.designSystem.component.textField.currency.rememberCurrencyTextFieldState
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.addGoal.component.ImageGoalPicker
import com.kakapo.oakane.presentation.ui.component.dialog.CustomDatePickerDialog
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEffect
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalEvent
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalState
import com.kakapo.oakane.presentation.viewModel.addGoal.AddGoalViewModel
import com.kakapo.oakane.presentation.viewModel.addGoal.GoalDateContent
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun AddGoalRoute(
    goalId: Long,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current
    val viewModel = koinViewModel<AddGoalViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initData(goalId)
    }

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
                when (uiState.dialogContent) {
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
            val title = if (uiState.isEditMode) "Edit Goal" else "Add Goal"
            CustomNavigationTopAppBarView(
                title = title,
                onNavigateBack = { onEvent.invoke(AddGoalEvent.NavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ImageGoalPicker(
                    imageUrl = uiState.fileName,
                    onSelectedImage = { onEvent.invoke(AddGoalEvent.SetFile(it)) }
                )
                Spacer(modifier = Modifier.size(4.dp))
                GoalCurrencyTextField(uiState, onEvent)
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
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.startDate.toDateWith(format = "dd MMM yyyy"),
                    placeHolder = "Starting Date",
                    trailingIcon = Icons.Outlined.CalendarToday,
                    onClick = { onEvent.invoke(AddGoalEvent.ShowDialog(GoalDateContent.Start)) }
                )
                CustomClickableOutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = uiState.endDate.toDateWith(format = "dd MMM yyyy"),
                    placeHolder = "End Date",
                    trailingIcon = Icons.Outlined.Event,
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

@Composable
private fun GoalCurrencyTextField(uiState: AddGoalState, onEvent: (AddGoalEvent) -> Unit) {
    val currencyTextFieldState = rememberCurrencyTextFieldState(
        config = CurrencyTextFieldConfig(
            Locale(uiState.currency.languageCode, uiState.currency.countryCode),
            currencySymbol = uiState.currency.symbol
        )
    ) {
        onEvent.invoke(AddGoalEvent.SetTarget(it))
    }
    OutlinedCurrencyTextFieldView(
        state = currencyTextFieldState,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Target") },
    )
}