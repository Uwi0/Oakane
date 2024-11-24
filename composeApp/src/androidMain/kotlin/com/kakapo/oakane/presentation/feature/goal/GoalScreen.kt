package com.kakapo.oakane.presentation.feature.goal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView

@Composable
fun GoalRoute() {
    GoalScreen()
}

@Composable
private fun GoalScreen() {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Goal",
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

            }
        }
    )
}