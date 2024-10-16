package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionTopAppBarView

@Composable
internal fun TransactionsRoute(navigateBack: () -> Unit) {

    val state = rememberTransactionUiState()
    val onEvent: (TransactionsUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    TransactionsScreen(state = state, onEvent = onEvent)
}

@Composable
private fun TransactionsScreen(state: TransactionsUiState, onEvent: (TransactionsUiEvent) -> Unit) {
    Scaffold(
        topBar = {
            TransactionTopAppBarView(state = state, onEvent = onEvent)
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

