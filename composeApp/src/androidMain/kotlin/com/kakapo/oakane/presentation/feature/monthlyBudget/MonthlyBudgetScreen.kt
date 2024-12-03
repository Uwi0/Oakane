package com.kakapo.oakane.presentation.feature.monthlyBudget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MonthlyBudgetRoute() {
    MonthlyBudgetScreen()
}

@Composable
private fun MonthlyBudgetScreen() {
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text("Hello world")
            }
        }
    )
}