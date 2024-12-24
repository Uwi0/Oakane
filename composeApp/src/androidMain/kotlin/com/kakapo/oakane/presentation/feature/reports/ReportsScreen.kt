package com.kakapo.oakane.presentation.feature.reports

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.presentation.feature.reports.component.DonutChartView
import com.kakapo.oakane.presentation.viewModel.reports.ReportsState
import com.kakapo.oakane.presentation.viewModel.reports.ReportsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ReportsRoute() {
    val viewModel = koinViewModel<ReportsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ReportsScreen(uiState = uiState)
}

@Composable
private fun ReportsScreen(uiState: ReportsState) {
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Reports")
                DonutChartView(proportions = uiState.proportions, colorsInt = uiState.colors)
            }
        }
    )
}