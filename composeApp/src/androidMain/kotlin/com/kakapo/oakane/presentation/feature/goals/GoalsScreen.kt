package com.kakapo.oakane.presentation.feature.goals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.feature.goals.component.GoalsTopAppbarView

@Composable
internal fun GoalRoute() {
    GoalScreen()
}

@Composable
private fun GoalScreen() {
    Scaffold(
        topBar = {
            GoalsTopAppbarView()
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp)
            ) {

            }
        }
    )
}