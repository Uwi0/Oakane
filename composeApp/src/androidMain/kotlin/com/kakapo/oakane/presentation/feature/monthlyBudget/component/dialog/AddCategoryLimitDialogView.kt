package com.kakapo.oakane.presentation.feature.monthlyBudget.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.viewModel.monthlyBudget.MonthlyBudgetEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddCategoryLimitDialogView(onEvent: (MonthlyBudgetEvent) -> Unit) {
    BasicAlertDialog(
        onDismissRequest = { onEvent.invoke(MonthlyBudgetEvent.Dialog(shown = false)) }
    ) {
        AddCategoryLimitDialogContentView(onEvent = onEvent)
    }
}

@Composable
private fun AddCategoryLimitDialogContentView(
    onEvent: (MonthlyBudgetEvent) -> Unit
) {
    Surface(shape = RoundedCornerShape(28.dp)) {
        Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
            Text("Title")
            Spacer(modifier = Modifier.padding(16.dp))
            Text("limit amount")
            Spacer(modifier = Modifier.padding(16.dp))
            Text("category")
            Spacer(modifier = Modifier.padding(24.dp))
            Text("button action")
        }
    }
}