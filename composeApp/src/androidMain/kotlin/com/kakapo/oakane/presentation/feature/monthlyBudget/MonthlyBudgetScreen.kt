package com.kakapo.oakane.presentation.feature.monthlyBudget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.button.CustomButton
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyBottomContentView
import com.kakapo.oakane.presentation.feature.monthlyBudget.component.MonthlyTopContentView

@Composable
internal fun MonthlyBudgetRoute() {
    MonthlyBudgetScreen()
}

@Composable
private fun MonthlyBudgetScreen() {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(title = "Monthly Budget") { }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                MonthlyTopContentView()
                MonthlyBottomContentView()
            }
        },
        bottomBar = {
            CustomButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp, start = 16.dp, end = 16.dp),
                onClick = {}) {
                Text("Save Budget")
            }
        }
    )
}

