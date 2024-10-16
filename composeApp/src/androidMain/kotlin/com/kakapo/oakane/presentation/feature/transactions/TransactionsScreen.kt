package com.kakapo.oakane.presentation.feature.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kakapo.oakane.model.transaction.TransactionModel
import com.kakapo.oakane.presentation.feature.transactions.component.TransactionTopAppBarView
import com.kakapo.oakane.presentation.ui.component.item.TransactionItemView
import com.kakapo.oakane.presentation.viewModel.transactions.TransactionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionsRoute(navigateBack: () -> Unit) {
    val viewModel = koinViewModel<TransactionsViewModel>()
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val state = rememberTransactionUiState()
    val onEvent: (TransactionsUiEvent) -> Unit = {
        when (it) {
            OnNavigateBack -> navigateBack.invoke()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    TransactionsScreen(state = state, transactions = transactions, onEvent = onEvent)
}

@Composable
private fun TransactionsScreen(
    state: TransactionsUiState,
    transactions: List<TransactionModel>,
    onEvent: (TransactionsUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TransactionTopAppBarView(state = state, onEvent = onEvent)
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 24.dp, horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(transactions) { transaction ->
                    TransactionItemView(transaction = transaction, onClick = {})
                }
            }
        }
    )
}

