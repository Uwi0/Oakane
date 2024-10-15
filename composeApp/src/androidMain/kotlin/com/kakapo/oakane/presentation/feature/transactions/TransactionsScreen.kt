package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kakapo.oakane.presentation.designSystem.component.topAppBar.CustomNavigationTopAppBarView

@Composable
internal fun TransactionsRoute(navigateBack: () -> Unit) {

    val onEvent: (TransactionsUiEvent) -> Unit = {
        when(it){
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    TransactionsScreen(onEvent = onEvent)
}

@Composable
private fun TransactionsScreen(onEvent: (TransactionsUiEvent) -> Unit) {
    Scaffold(
        topBar = {
            CustomNavigationTopAppBarView(
                title = "Transactions",
                shadowElevation = 0.dp,
                tonalElevation = 0.dp,
                onNavigateBack = {
                    onEvent.invoke(OnNavigateBack)
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
            ) {

            }
        }
    )
}

