package com.kakapo.oakane.presentation.feature.addTransaction

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView

@Composable
internal fun AddTransactionRoute(navigateBack: () -> Unit) {

    val onEvent: (AddTransactionUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    AddTransactionScreen(onEvent = onEvent)
}

@Composable
private fun AddTransactionScreen(onEvent: (AddTransactionUiEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Add Transaction",
                onNavigateBack = { onEvent.invoke(OnNavigateBack) }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(text = "Add transaction screen")
            }
        }
    )
}