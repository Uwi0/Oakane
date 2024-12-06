package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.BudgetTextFieldView
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCategoryLimitDialogView(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = false)) }
    ) {
        AddCategoryLimitDialogContentView(uiState = uiState, onEvent = onEvent)
    }
}

@Composable
private fun AddCategoryLimitDialogContentView(
    uiState: MonthlyBudgetState,
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    Surface(shape = RoundedCornerShape(28.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text("Add your Category Limit")
            Spacer(modifier = Modifier.size(16.dp))
            BudgetTextFieldView(value = "", onValueChange = {})
            Spacer(modifier = Modifier.size(8.dp))
            CategoryLimitDropdownMenuView(expenseCategories = uiState.expenseCategories)
            Spacer(modifier = Modifier.size(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = false)) }
                ) {
                    Text("Cancel")
                }
                Spacer(Modifier.size(16.dp))
                CustomButton(
                    onClick = {}
                ) {
                    Text("Save Limit")
                }
            }
        }
    }
}